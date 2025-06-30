const API_BASE = 'http://localhost:8080/api/api';
const token = localStorage.getItem('jwt');

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to view user details.');
    window.location.href = 'login.html';
    return;
  }
  loadUserDetails();
  loadRoleBasedButtons();
});

function loadUserDetails() {
  let userData = JSON.parse(localStorage.getItem('userData'));
  if (!userData || !userData.idAccount) {
    const headers = {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };

    fetch(`${API_BASE}/user/me`, {
      method: 'GET',
      headers: headers
    })
    .then(res => {
      if (res.status === 401 || res.status === 403) {
        alert('Session expired or insufficient permissions. Please log in again.');
        window.location.href = 'login.html';
        return;
      }
      if (!res.ok) {
        throw new Error(`HTTP error! Status: ${res.status}`);
      }
      return res.json();
    })
    .then(data => {
      userData = {
        idAccount: data.account.id,
        name: data.name,
        position: data.position,
        identifier: data.identifier,
        department: data.department,
        idSchool: data.school?.id
      };
      localStorage.setItem('userData', JSON.stringify(userData));
      updateDisplay(userData);
    })
    .catch(err => {
      console.error('Error loading user details:', err);
      alert('Failed to load user details. Check console or try logging in again.');
    });
  } else {
    updateDisplay(userData);
  }
}

function updateDisplay(userData) {
  document.getElementById('idAccount').textContent = userData.idAccount || '';
  document.getElementById('name').textContent = userData.name || '';
  document.getElementById('position').textContent = userData.position || '';
  document.getElementById('identifier').textContent = userData.identifier || '';
  document.getElementById('department').textContent = userData.department || '';
  document.getElementById('idSchool').textContent = userData.idSchool || '';
}

function loadRoleBasedButtons() {
  const managementButtons = document.getElementById('managementButtons');
  managementButtons.innerHTML = '';
  if (token) {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const role = payload.role;

    if (role === 'ROLE_ADMIN' || role === 'ROLE_DEAN') {
      const curriculumBtn = document.createElement('button');
      curriculumBtn.textContent = 'Curriculum Management';
      curriculumBtn.onclick = () => window.location.href = 'curriculum.html';
      managementButtons.appendChild(curriculumBtn);
    }
    if (role === 'ROLE_DEAN') {
      const schoolBtn = document.createElement('button');
      schoolBtn.textContent = 'School Management';
      schoolBtn.onclick = () => window.location.href = 'school.html';
      managementButtons.appendChild(schoolBtn);
    }
  }
}

function toggleUpdateForm() {
  const form = document.getElementById('updateForm');
  const isVisible = form.style.display === 'block';
  form.style.display = isVisible ? 'none' : 'block';
  if (!isVisible) {
    const userData = JSON.parse(localStorage.getItem('userData'));
    document.getElementById('updateName').value = userData.name || '';
    document.getElementById('updatePosition').value = userData.position || '';
    document.getElementById('updateIdentifier').value = userData.identifier || '';
    document.getElementById('updateDepartment').value = userData.department || '';
    document.getElementById('updateIdSchool').value = userData.idSchool || '';
  }
}

function updateUserInfo(event) {
  event.preventDefault();
  const idAccount = JSON.parse(localStorage.getItem('userData')).idAccount;
  const userDTO = {
    name: document.getElementById('updateName').value,
    position: document.getElementById('updatePosition').value,
    identifier: document.getElementById('updateIdentifier').value,
    department: document.getElementById('updateDepartment').value,
    idSchool: parseInt(document.getElementById('updateIdSchool').value)
  };

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  fetch(`${API_BASE}/user/update/${idAccount}`, {
    method: 'PUT',
    headers: headers,
    body: JSON.stringify(userDTO)
  })
  .then(res => {
    if (res.status === 401 || res.status === 403) {
      alert('Session expired or insufficient permissions. Please log in again.');
      window.location.href = 'login.html';
      return;
    }
    if (!res.ok) {
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
    return res.json();
  })
  .then(data => {
    alert('User information updated successfully.');
    localStorage.setItem('userData', JSON.stringify({
      idAccount: data.account.id,
      name: data.name,
      position: data.position,
      identifier: data.identifier,
      department: data.department,
      idSchool: data.school?.id
    }));
    loadUserDetails();
    toggleUpdateForm();
  })
  .catch(err => {
    console.error('Error updating user info:', err);
    alert('Failed to update user information. Check console for details.');
  });
}

function showUpdatePasswordForm() {
  document.getElementById('passwordForm').style.display = 'block';
}

function hidePasswordForm() {
  document.getElementById('passwordForm').style.display = 'none';
  document.getElementById('updatePasswordForm').reset();
}

function changePassword(event) {
  event.preventDefault();
  const accountDTO = {
    gmail: JSON.parse(atob(token.split('.')[1])).sub,
    password: document.getElementById('currentPassword').value,
    updatePassword: document.getElementById('newPassword').value
  };

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  fetch(`${API_BASE}/account/auth/change-password`, {
    method: 'PATCH',
    headers: headers,
    body: JSON.stringify(accountDTO)
  })
  .then(res => {
    if (res.status === 401 || res.status === 403) {
      alert('Session expired or insufficient permissions. Please log in again.');
      window.location.href = 'login.html';
      return;
    }
    if (!res.ok) {
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
    return res.text();
  })
  .then(message => {
    alert(message);
    hidePasswordForm();
  })
  .catch(err => {
    console.error('Error changing password:', err);
    alert('Failed to change password. Check console for details.');
  });
}

document.getElementById('updateUserForm').addEventListener('submit', updateUserInfo);
document.getElementById('updatePasswordForm').addEventListener('submit', changePassword);