const API_BASE = 'http://localhost:8080/api/api'; 

const token = localStorage.getItem('jwt');

document.addEventListener('DOMContentLoaded', loadCurriculums);
document.getElementById('curriculumForm').addEventListener('submit', submitForm);

function loadCurriculums() {
  if (!token) {
    console.error('No JWT token found. Please log in.');
    alert('Please log in to access curriculums.');
    window.location.href = 'login.html';
    return;
  }

  const headers = { 
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  fetch(`${API_BASE}/curriculums/get`, { 
    method: 'GET',
    headers: headers
  })
  .then(res => {
    console.log('Response status:', res.status); 
    if (!res.ok) {
      throw new Error(`HTTP error! Status: ${res.status} - ${res.statusText}`);
    }
    return res.json();
  })
  .then(data => {
    const tbody = document.querySelector('#curriculumTable tbody');
    tbody.innerHTML = '';
    if (Array.isArray(data)) {
      data.forEach(c => {
        const row = `
          <tr>
            <td>${c.curriculumCode || ''}</td>
            <td>${c.nameCurriculum || ''}</td>
            <td>${c.lecturer || ''}</td>
            <td>${c.description || ''}</td>
            <td>${c.url ? `<a href="#" onclick="viewCurriculum('${c.curriculumCode}')">View</a>` : ''}</td>
            <td>
              <button onclick='editCurriculum(${JSON.stringify(c)})'>Edit</button>
              <button onclick='deleteCurriculum("${c.curriculumCode || ''}")'>Delete</button>
            </td>
          </tr>`;
        tbody.insertAdjacentHTML('beforeend', row);
      });
    } else {
      console.error('Unexpected data format:', data);
      alert('Failed to load curriculums due to unexpected data format.');
    }
  })
  .catch(err => {
    console.error('Error loading curriculums:', err);
    alert('Failed to load curriculums. Check console for details. Status: ' + err.message);
  });
}

function showAddForm() {
  document.getElementById('form-section').style.display = 'block';
  document.getElementById('form-title').textContent = 'Add Curriculum';
  document.getElementById('curriculumForm').reset();
  document.getElementById('curriculumCode').value = ''; 
  document.getElementById('curriculumCodeInput').value = ''; 
}

function hideForm() {
  document.getElementById('form-section').style.display = 'none';
}

function submitForm(event) {
  event.preventDefault();
  if (!token) {
    alert('Please log in to perform this action.');
    window.location.href = 'login.html';
    return;
  }

  const formData = new FormData();
  const codeFromHidden = document.getElementById('curriculumCode').value;
  const codeFromInput = document.getElementById('curriculumCodeInput').value;
  const code = codeFromHidden || codeFromInput; 
  const isEdit = !!codeFromHidden; 
  const method = isEdit ? 'PUT' : 'POST';
  const url = isEdit ? `${API_BASE}/curriculums/update/${code}` : `${API_BASE}/curriculums/add`;

  formData.append('curriculumCode', code);
  formData.append('nameCurriculum', document.getElementById('nameCurriculum').value);
  formData.append('lecturer', document.getElementById('lecturer').value);
  formData.append('description', document.getElementById('description').value);
  formData.append('identifier', document.getElementById('identifier').value);
  formData.append('subject', document.getElementById('subject').value);
  formData.append('branch', document.getElementById('branch').value);
  formData.append('department', document.getElementById('department').value);
  formData.append('subjectCode', document.getElementById('subjectCode').value);
  formData.append('branchCode', document.getElementById('branchCode').value);
  formData.append('departmentCode', document.getElementById('departmentCode').value);

  const file = document.getElementById('fileUpload').files[0];
  if (file) {
    if (file.size > 20 * 1024 * 1024) { 
      alert('File size must not exceed 20MB.');
      return;
    }
    if (!file.type === 'application/pdf') { 
      alert('Only PDF files are allowed.');
      return;
    }
    formData.append('file', file);
  }

  const headers = { 
    'Authorization': `Bearer ${token}` 
  };

  fetch(url, {
    method: method,
    headers: headers,
    body: formData
  })
  .then(res => {
    if (!res.ok) {
      return res.json().then(error => { throw new Error(error.message || `Error submitting curriculum: ${res.status} - ${res.statusText}`); });
    }
    return res.json();
  })
  .then(() => {
    hideForm();
    loadCurriculums();
  })
  .catch(err => {
    console.error('Submission error:', err);
    alert(err.message);
  });
}

function editCurriculum(c) {
  showAddForm();
  document.getElementById('form-title').textContent = 'Edit Curriculum';
  document.getElementById('curriculumCode').value = c.curriculumCode || ''; 
  document.getElementById('curriculumCodeInput').value = c.curriculumCode || ''; 
  document.getElementById('nameCurriculum').value = c.nameCurriculum || '';
  document.getElementById('lecturer').value = c.lecturer || '';
  document.getElementById('description').value = c.description || '';
  document.getElementById('identifier').value = c.identifier || '';
  document.getElementById('subject').value = c.subject || '';
  document.getElementById('branch').value = c.branch || '';
  document.getElementById('department').value = c.department || '';
  document.getElementById('subjectCode').value = c.subjectCode || '';
  document.getElementById('branchCode').value = c.branchCode || '';
  document.getElementById('departmentCode').value = c.departmentCode || '';
}

function deleteCurriculum(code) {
  if (!confirm('Are you sure you want to delete this curriculum?')) return;

  const headers = { 'Authorization': `Bearer ${token}` };

  fetch(`${API_BASE}/curriculums/delete/${code}`, {
    method: 'DELETE',
    headers: headers
  })
  .then(res => {
    if (!res.ok) throw new Error(`Failed to delete: ${res.status} - ${res.statusText}`);
    loadCurriculums();
  })
  .catch(err => alert(err.message));
}

function filterCurriculums() {
  if (!token) {
    alert('Please log in to filter curriculums.');
    window.location.href = 'login.html';
    return;
  }

  const params = new URLSearchParams({
    name: document.getElementById('filterName').value,
    lecturer: document.getElementById('filterLecturer').value,
    identifier: document.getElementById('filterIdentifier').value,
    subject: document.getElementById('filterSubject').value,
    branch: document.getElementById('filterBranch').value,
    department: document.getElementById('filterDepartment').value
  });

  const headers = { 
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  fetch(`${API_BASE}/curriculums/filter?${params}`, {
    method: 'GET',
    headers: headers
  })
  .then(res => {
    if (!res.ok) throw new Error(`Filter failed: ${res.status} - ${res.statusText}`);
    return res.json();
  })
  .then(data => {
    const tbody = document.querySelector('#curriculumTable tbody');
    tbody.innerHTML = '';
    if (Array.isArray(data)) {
      data.forEach(c => {
        const row = `
          <tr>
            <td>${c.curriculumCode || ''}</td>
            <td>${c.nameCurriculum || ''}</td>
            <td>${c.lecturer || ''}</td>
            <td>${c.description || ''}</td>
            <td>${c.url ? `<a href="#" onclick="viewCurriculum('${c.curriculumCode}')">View</a>` : ''}</td>
            <td>
              <button onclick='editCurriculum(${JSON.stringify(c)})'>Edit</button>
              <button onclick='deleteCurriculum("${c.curriculumCode || ''}")'>Delete</button>
            </td>
          </tr>`;
        tbody.insertAdjacentHTML('beforeend', row);
      });
    } else {
      console.error('Unexpected data format:', data);
      alert('Filter returned unexpected data format.');
    }
  })
  .catch(err => alert(err.message));
}

function viewCurriculum(curriculumCode) {
  if (!token) {
    alert('Please log in to view curriculum.');
    window.location.href = 'login.html';
    return;
  }

  window.location.href = `view-curriculum.html?code=${encodeURIComponent(curriculumCode)}`;
}

function goBackToUserDetails() {
  const token = localStorage.getItem('jwt');
  const userData = localStorage.getItem('userData');
  if (!token || !userData) {
    alert('Please log in again to access your user details.');
    window.location.href = 'login.html';
    return;
  }
  window.location.href = 'user_details.html';
}
