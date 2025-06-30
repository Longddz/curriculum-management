const API_BASE = 'http://localhost:8080/api/api';
const token = localStorage.getItem('jwt');

const urlParams = new URLSearchParams(window.location.search);
const code = urlParams.get('code');

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to view curriculum.');
    window.location.href = 'login.html';
    return;
  }

  if (!code) {
    alert('No curriculum code provided.');
    window.location.href = 'curriculum.html';
    return;
  }

  loadCurriculumDetails(code);
});

function loadCurriculumDetails(code) {
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  fetch(`${API_BASE}/curriculums/get/${code}`, {
    method: 'GET',
    headers: headers
  })
  .then(res => {
    if (res.status === 401) {
      alert('Session expired. Please log in again.');
      window.location.href = 'login.html';
      return;
    }
    if (!res.ok) {
      throw new Error(`HTTP error! Status: ${res.status} - ${res.statusText}`);
    }
    return res.json();
  })
  .then(data => {
    if (data) {
      const c = data; 
      document.getElementById('code').textContent = c.curriculumCode || '';
      document.getElementById('name').textContent = c.nameCurriculum || '';
      document.getElementById('lecturer').textContent = c.lecturer || '';
      document.getElementById('description').textContent = c.description || '';
      document.getElementById('subject').textContent = c.subject || '';
      document.getElementById('branch').textContent = c.branch || '';
      document.getElementById('department').textContent = c.department || '';
      document.getElementById('subjectCode').textContent = c.subjectCode || '';
      document.getElementById('branchCode').textContent = c.branchCode || '';
      document.getElementById('departmentCode').textContent = c.departmentCode || '';
      const fileLink = document.getElementById('fileLink');
      if (c.url) {
        fileLink.href = c.url;
        fileLink.style.display = 'inline';
      } else {
        document.getElementById('file').textContent = 'No file available';
        fileLink.style.display = 'none';
      }
    } else {
      alert('Curriculum not found.');
      window.location.href = 'curriculum.html';
    }
  })
  .catch(err => {
    console.error('Error loading curriculum details:', err);
    alert('Failed to load curriculum details. Check console for details.');
  });
}

function goBack() {
  window.location.href = 'curriculum.html';
}