const API_BASE = 'http://localhost:8080/api/api/account';

// LOGIN
const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', function(e) {
    e.preventDefault();

    const gmail = document.getElementById('gmail').value;
    const password = document.getElementById('password').value;
    console.log('Login attempt - gmail:', gmail, 'password:', password); 

    fetch(`${API_BASE}/auth/login`, { 
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ gmail, password })
    })
    .then(res => {
      console.log('Response status:', res.status); 
      return res.json(); 
    })
    .then(data => {
      console.log('Received data:', data); 
      const token = data.token; 
      if (token && token.startsWith('ey')) {
        localStorage.setItem('jwt', token);
        alert('Login successful!');
        window.location.href = 'index.html';
      } else {
        alert('Invalid credentials');
      }
    })
    .catch(err => {
      console.error('Login error:', err); 
      alert('Login failed');
    });
  });
}



// REGISTER
const registerForm = document.getElementById('registerForm');
if (registerForm) {
  registerForm.addEventListener('submit', function(e) {
    e.preventDefault();

    const gmail = document.getElementById('gmail').value;
    const password = document.getElementById('password').value;
    const roles = document.getElementById('role').value;
    let role;
    if(roles === 'USER')
    {
        role = 'ROLE_USER';
    }
    else if(roles === 'ADMIN')
    {
        role = 'ROLE_ADMIN';
    }
    else
    {
        role = 'ROLE_DEAN';
    }

    fetch(`${API_BASE}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ gmail, password, role })
    })
    .then(res => {
      if (!res.ok) throw new Error('Register failed or email already exists');
      return res.json();
    })
    .then(data => {
      alert('Register successful!');
      window.location.href = 'login.html';
    })
    .catch(err => alert(err.message));
  });
}
