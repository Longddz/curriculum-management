const API_BASE = 'http://localhost:8080/api/api';
const token = localStorage.getItem('jwt');

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to manage schools.');
    window.location.href = 'login.html';
    return;
  }
  loadSchools();
});

function loadSchools() {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/schools/get`, { method: 'GET', headers })
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
      const tbody = document.getElementById('schoolBody');
      tbody.innerHTML = '';
      data.forEach(school => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${school.id}</td>
          <td>${school.department}</td>
          <td>${school.departmentCode}</td>
          <td>
            <button class="edit-btn" onclick="editSchool(${school.id})">Edit</button>
            <button class="delete-btn" onclick="deleteSchool(${school.id})">Delete</button>
            <button class="nav-button" onclick="goToBranchPage(${school.id})">List of majors in the faculty</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(err => {
      console.error('Error loading schools:', err);
      alert('Failed to load schools. Check console for details.');
    });
}

function showAddForm() {
  document.getElementById('schoolForm').style.display = 'block';
  document.getElementById('formTitle').textContent = 'Add School';
  document.getElementById('schoolFormData').reset();
  document.getElementById('schoolId').value = '';
}

function editSchool(id) {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/schools/get/${id}`, { method: 'GET', headers })
    .then(res => {
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(school => {
      document.getElementById('schoolForm').style.display = 'block';
      document.getElementById('formTitle').textContent = 'Update School';
      document.getElementById('schoolId').value = school.id;
      document.getElementById('department').value = school.department;
      document.getElementById('departmentCode').value = school.departmentCode;
    })
    .catch(err => console.error('Error loading school:', err));
}

function deleteSchool(id) {
  if (confirm('Are you sure you want to delete this school?')) {
    const headers = { 'Authorization': `Bearer ${token}` };
    fetch(`${API_BASE}/schools/delete/${id}`, { method: 'DELETE', headers })
      .then(res => {
        if (res.status === 401) {
          alert('Session expired. Please log in again.');
          window.location.href = 'login.html';
          return;
        }
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        loadSchools();
      })
      .catch(err => {
        console.error('Error deleting school:', err);
        alert('Failed to delete school. Check console for details.');
      });
  }
}

function hideForm() {
  document.getElementById('schoolForm').style.display = 'none';
}

function saveSchool(event) {
  event.preventDefault();
  const id = document.getElementById('schoolId').value;
  const schoolDTO = {
    department: document.getElementById('department').value,
    departmentCode: document.getElementById('departmentCode').value
  };
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  const url = id ? `${API_BASE}/schools/update/${id}` : `${API_BASE}/schools/add`;
  const method = id ? 'PUT' : 'POST';

  fetch(url, { method, headers, body: JSON.stringify(schoolDTO) })
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
      alert('School saved successfully.');
      hideForm();
      loadSchools();
    })
    .catch(err => {
      console.error('Error saving school:', err);
      alert('Failed to save school. Check console for details.');
    });
}

function goToBranchPage(schoolId) {
  window.location.href = `branch.html?schoolId=${schoolId}`;
}

document.getElementById('schoolFormData').addEventListener('submit', saveSchool);