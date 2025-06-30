const API_BASE = 'http://localhost:8080/api/api';
const token = localStorage.getItem('jwt');

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to manage subjects.');
    window.location.href = 'login.html';
    return;
  }
  const urlParams = new URLSearchParams(window.location.search);
  const branchId = urlParams.get('branchId');
  if (branchId) {
    loadSubjectsByBranch(branchId);
  } else {
    loadSubjects();
  }
});

function loadSubjects() {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/subjects/get`, { method: 'GET', headers })
    .then(res => {
      if (res.status === 401) {
        alert('Session expired. Please log in again.');
        window.location.href = 'login.html';
        return;
      }
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(data => {
      const tbody = document.getElementById('subjectBody');
      tbody.innerHTML = '';
      if (data.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td colspan="5" style="text-align: center;">Hiện ngành chưa có môn nào</td>
        `;
        tbody.appendChild(row);
      } else {
        data.forEach(subject => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${subject.id}</td>
            <td>${subject.branch.branch}</td>
            <td>${subject.subject}</td>
            <td>${subject.subjectCode}</td>
            <td>
              <button class="edit-btn" onclick="editSubject(${subject.id})">Edit</button>
              <button class="delete-btn" onclick="deleteSubject(${subject.id})">Delete</button>
            </td>
          `;
          tbody.appendChild(row);
        });
      }
    })
    .catch(err => {
      console.error('Error loading subjects:', err);
      alert('Failed to load subjects. Check console for details.');
    });
}

function loadSubjectsByBranch(branchId) {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/subjects/get?branchId=${branchId}`, { method: 'GET', headers })
    .then(res => {
      if (res.status === 401) {
        alert('Session expired. Please log in again.');
        window.location.href = 'login.html';
        return;
      }
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(data => {
      const tbody = document.getElementById('subjectBody');
      tbody.innerHTML = '';
      if (data.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td colspan="5" style="text-align: center;">Hiện ngành chưa có môn nào</td>
        `;
        tbody.appendChild(row);
      } else {
        data.forEach(subject => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${subject.id}</td>
            <td>${subject.branch.branch}</td>
            <td>${subject.subject}</td>
            <td>${subject.subjectCode}</td>
            <td>
              <button class="edit-btn" onclick="editSubject(${subject.id})">Edit</button>
              <button class="delete-btn" onclick="deleteSubject(${subject.id})">Delete</button>
            </td>
          `;
          tbody.appendChild(row);
        });
      }
    })
    .catch(err => {
      console.error('Error loading subjects by branch:', err);
      alert('Failed to load subjects. Check console for details.');
    });
}

function showAddForm() {
  document.getElementById('subjectForm').style.display = 'block';
  document.getElementById('formTitle').textContent = 'Add Subject';
  document.getElementById('subjectFormData').reset();
  document.getElementById('subjectId').value = '';
}

function editSubject(id) {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/subjects/get/${id}`, { method: 'GET', headers })
    .then(res => {
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(subject => {
      document.getElementById('subjectForm').style.display = 'block';
      document.getElementById('formTitle').textContent = 'Update Subject';
      document.getElementById('subjectId').value = subject.id;
      document.getElementById('subject').value = subject.subject;
      document.getElementById('subjectCode').value = subject.subjectCode;
      document.getElementById('branchId').value = subject.branch.id;
    })
    .catch(err => console.error('Error loading subject:', err));
}

function deleteSubject(id) {
  if (confirm('Are you sure you want to delete this subject?')) {
    const headers = { 'Authorization': `Bearer ${token}` };
    fetch(`${API_BASE}/subjects/delete/${id}`, { method: 'DELETE', headers })
      .then(res => {
        if (res.status === 401) {
          alert('Session expired. Please log in again.');
          window.location.href = 'login.html';
          return;
        }
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        const urlParams = new URLSearchParams(window.location.search);
        const branchId = urlParams.get('branchId');
        if (branchId) {
          loadSubjectsByBranch(branchId);
        } else {
          loadSubjects();
        }
      })
      .catch(err => {
        console.error('Error deleting subject:', err);
        alert('Failed to delete subject. Check console for details.');
      });
  }
}

function hideForm() {
  document.getElementById('subjectForm').style.display = 'none';
}

function saveSubject(event) {
  event.preventDefault();
  const id = document.getElementById('subjectId').value;
  const subjectDTO = {
    subject: document.getElementById('subject').value,
    subjectCode: document.getElementById('subjectCode').value,
    branchId: parseInt(document.getElementById('branchId').value)
  };
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  const url = id ? `${API_BASE}/subjects/update/${id}` : `${API_BASE}/subjects/add`;
  const method = id ? 'PUT' : 'POST';

  fetch(url, { method, headers, body: JSON.stringify(subjectDTO) })
    .then(res => {
      if (res.status === 401) {
        alert('Session expired. Please log in again.');
        window.location.href = 'login.html';
        return;
      }
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(() => {
      alert('Subject saved successfully.');
      hideForm();
      const urlParams = new URLSearchParams(window.location.search);
      const branchId = urlParams.get('branchId');
      if (branchId) {
        loadSubjectsByBranch(branchId);
      } else {
        loadSubjects();
      }
    })
    .catch(err => {
      console.error('Error saving subject:', err);
      alert('Failed to save subject. Check console for details.');
    });
}

document.getElementById('subjectFormData').addEventListener('submit', saveSubject);