const API_BASE = 'http://localhost:8080/api/api';
const token = localStorage.getItem('jwt');

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to manage branches.');
    window.location.href = 'login.html';
    return;
  }
  const urlParams = new URLSearchParams(window.location.search);
  const schoolId = urlParams.get('schoolId');
  if (schoolId) {
    loadBranchesBySchoolId(schoolId);
  } else {
    loadBranches();
  }
});

function loadBranches() {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/branches/get`, { method: 'GET', headers })
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
      const tbody = document.getElementById('branchBody');
      tbody.innerHTML = '';
      if (data.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td colspan="5" style="text-align: center;">Hiện khoa chưa có ngành nào</td>
        `;
        tbody.appendChild(row);
      } else {
        data.forEach(branch => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${branch.school.id}</td>
            <td>${branch.id}</td>
            <td>${branch.branch}</td>
            <td>${branch.branchCode}</td>
            <td>
              <button class="edit-btn" onclick="editBranch(${branch.id})">Edit</button>
              <button class="delete-btn" onclick="deleteBranch(${branch.id})">Delete</button>
              <button class="nav-button" onclick="goToSubjectPage(${branch.id})">List of subjects in the faculty</button>
            </td>
          `;
          tbody.appendChild(row);
        });
      }
    })
    .catch(err => {
      console.error('Error loading branches:', err);
      alert('Failed to load branches. Check console for details.');
    });
}

function loadBranchesBySchoolId(schoolId) {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/branches/get?schoolId=${schoolId}`, { method: 'GET', headers })
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
      const tbody = document.getElementById('branchBody');
      tbody.innerHTML = '';
      if (data.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td colspan="5" style="text-align: center;">Hiện khoa chưa có ngành nào</td>
        `;
        tbody.appendChild(row);
      } else {
        data.forEach(branch => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${branch.school.id}</td>
            <td>${branch.id}</td>
            <td>${branch.branch}</td>
            <td>${branch.branchCode}</td>
            <td>
              <button class="edit-btn" onclick="editBranch(${branch.id})">Edit</button>
              <button class="delete-btn" onclick="deleteBranch(${branch.id})">Delete</button>
              <button class="nav-button" onclick="goToSubjectPage(${branch.id})">List of subjects in the faculty</button>
            </td>
          `;
          tbody.appendChild(row);
        });
      }
    })
    .catch(err => {
      console.error('Error loading branches by schoolId:', err);
      alert('Failed to load branches. Check console for details.');
    });
}

function showAddForm() {
  document.getElementById('branchForm').style.display = 'block';
  document.getElementById('formTitle').textContent = 'Add Branch';
  document.getElementById('branchFormData').reset();
  document.getElementById('branchId').value = '';
}

function editBranch(id) {
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  fetch(`${API_BASE}/branches/get/${id}`, { method: 'GET', headers })
    .then(res => {
      if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
      return res.json();
    })
    .then(branch => {
      document.getElementById('branchForm').style.display = 'block';
      document.getElementById('formTitle').textContent = 'Update Branch';
      document.getElementById('branchId').value = branch.id;
      document.getElementById('schoolId').value = branch.school.id;
      document.getElementById('branch').value = branch.branch;
      document.getElementById('branchCode').value = branch.branchCode;
    })
    .catch(err => console.error('Error loading branch:', err));
}

function deleteBranch(id) {
  if (confirm('Are you sure you want to delete this branch?')) {
    const headers = { 'Authorization': `Bearer ${token}` };
    fetch(`${API_BASE}/branches/delete/${id}`, { method: 'DELETE', headers })
      .then(res => {
        if (res.status === 401) {
          alert('Session expired. Please log in again.');
          window.location.href = 'login.html';
          return;
        }
        if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
        const urlParams = new URLSearchParams(window.location.search);
        const schoolId = urlParams.get('schoolId');
        if (schoolId) {
          loadBranchesBySchoolId(schoolId);
        } else {
          loadBranches();
        }
      })
      .catch(err => {
        console.error('Error deleting branch:', err);
        alert('Failed to delete branch. Check console for details.');
      });
  }
}

function hideForm() {
  document.getElementById('branchForm').style.display = 'none';
}

function saveBranch(event) {
  event.preventDefault();
  const id = document.getElementById('branchId').value;
  const branchDTO = {
    schoolId: parseInt(document.getElementById('schoolId').value),
    branch: document.getElementById('branch').value,
    branchCode: document.getElementById('branchCode').value
  };
  const headers = { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' };
  const url = id ? `${API_BASE}/branches/update/${id}` : `${API_BASE}/branches/add`;
  const method = id ? 'PUT' : 'POST';

  fetch(url, { method, headers, body: JSON.stringify(branchDTO) })
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
      alert('Branch saved successfully.');
      hideForm();
      const urlParams = new URLSearchParams(window.location.search);
      const schoolId = urlParams.get('schoolId');
      if (schoolId) {
        loadBranchesBySchoolId(schoolId);
      } else {
        loadBranches();
      }
    })
    .catch(err => {
      console.error('Error saving branch:', err);
      alert('Failed to save branch. Check console for details.');
    });
}

function goToSubjectPage(branchId) {
  window.location.href = `subject.html?branchId=${branchId}`;
}

document.getElementById('branchFormData').addEventListener('submit', saveBranch);