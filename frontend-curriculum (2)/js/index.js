// const API_BASE = 'http://localhost:8080/api/api';
// const token = localStorage.getItem('jwt');
// let currentPage = 1;
// const itemsPerPage = 6;

// document.addEventListener('DOMContentLoaded', () => {
//   if (!token) {
//     alert('Please log in to view curriculums.');
//     window.location.href = 'login.html';
//     return;
//   }
//   loadCurriculums();
// });

// function loadCurriculums() {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     displayCurriculums(data.content || data); // Handle both Page and List response
//     updatePagination(data.totalPages || 1, data.totalElements || data.length);
//   })
//   .catch(err => {
//     console.error('Error loading curriculums:', err);
//     alert('Failed to load curriculums. Check console for details.');
//   });
// }

// function displayCurriculums(curriculums) {
//   const list = document.getElementById('curriculumList');
//   list.innerHTML = '';
//   curriculums.forEach(c => {
//     const card = document.createElement('div');
//     card.className = 'curriculum-card';
//     card.innerHTML = `
//       <p><strong>Curriculum code:</strong> ${c.curriculumCode || ''}</p>
//       <p><strong>Name curriculum:</strong> ${c.nameCurriculum || ''}</p>
//       <p><strong>Lecturer:</strong> ${c.lecturer || ''}</p>
//       <p><strong>Description:</strong> ${c.description || ''}</p>
//       <p><strong>Department:</strong> ${c.department || ''}</p>
//       <p><strong>Subject:</strong> ${c.subject || ''}</p>
//       <p><strong>Branch:</strong> ${c.branch || ''}</p>
//       <button class="detail-btn" onclick="viewCurriculum('${c.curriculumCode}')">Detail</button>
//       <button class="download-btn" onclick="downloadCurriculum('${c.curriculumCode}')">Download</button>
//     `;
//     list.appendChild(card);
//   });
// }

// function updatePagination(totalPages, totalElements) {
//   const prevBtn = document.getElementById('prevBtn');
//   const nextBtn = document.getElementById('nextBtn');
//   const pageInfo = document.getElementById('pageInfo');

//   prevBtn.disabled = currentPage === 1;
//   nextBtn.disabled = currentPage === totalPages;
//   pageInfo.textContent = `Page ${currentPage} of ${totalPages} (Total: ${totalElements} items)`;
// }

// function prevPage() {
//   if (currentPage > 1) {
//     currentPage--;
//     loadCurriculums();
//   }
// }

// function nextPage() {
//   currentPage++;
//   loadCurriculums();
// }

// function filterCurriculums() {
//   const searchTerm = document.getElementById('searchInput').value.toLowerCase();
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/filter?name=${searchTerm}&lecturer=${searchTerm}&subject=${searchTerm}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     displayCurriculums(data);
//     updatePagination(1, data.length); // Reset pagination for filter
//     currentPage = 1;
//   })
//   .catch(err => {
//     console.error('Error filtering curriculums:', err);
//     alert('Failed to filter curriculums. Check console for details.');
//   });
// }

// function viewCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     document.getElementById('detailCode').textContent = data.curriculumCode || '';
//     document.getElementById('detailName').textContent = data.nameCurriculum || '';
//     document.getElementById('detailLecturer').textContent = data.lecturer || '';
//     document.getElementById('detailSubject').textContent = data.subject || '';
//     document.getElementById('detailBranch').textContent = data.branch || '';
//     document.getElementById('detailDepartment').textContent = data.department || '';
//     document.getElementById('detailDescription').textContent = data.description || '';
//     const urlLink = document.getElementById('detailUrlLink');
//     if (data.url) {
//       urlLink.href = data.url;
//       urlLink.style.display = 'inline';
//       document.getElementById('detailUrl').style.display = 'block';
//     } else {
//       document.getElementById('detailUrl').textContent = 'No file available';
//       urlLink.style.display = 'none';
//     }
//     document.getElementById('detailsContainer').style.display = 'block';
//   })
//   .catch(err => {
//     console.error('Error loading curriculum details:', err);
//     alert('Failed to load curriculum details. Check console for details.');
//   });
// }

// function downloadCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     if (data.url) {
//       window.open(data.url, '_blank');
//     } else {
//       alert('No file available for download.');
//     }
//   })
//   .catch(err => {
//     console.error('Error downloading curriculum:', err);
//     alert('Failed to download curriculum. Check console for details.');
//   });
// }

// function hideDetails() {
//   document.getElementById('detailsContainer').style.display = 'none';
// }

// function downloadFile() {
//   const url = document.getElementById('detailUrlLink').href;
//   if (url && url !== '#') {
//     window.open(url, '_blank');
//   } else {
//     alert('No file available for download.');
//   }
// }



// const API_BASE = 'http://localhost:8080/api/api';
// const token = localStorage.getItem('jwt');
// let currentPage = 1;
// const itemsPerPage = 6;

// document.addEventListener('DOMContentLoaded', () => {
//   if (!token) {
//     alert('Please log in to view curriculums.');
//     window.location.href = 'login.html';
//     return;
//   }
//   loadCurriculums();
// });

// function loadCurriculums() {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     displayCurriculums(data.content || data);
//     updatePagination(data.totalPages || 1, data.totalElements || data.length);
//   })
//   .catch(err => {
//     console.error('Error loading curriculums:', err);
//     alert('Failed to load curriculums. Check console for details.');
//   });
// }

// function displayCurriculums(curriculums) {
//   const list = document.getElementById('curriculumList');
//   list.innerHTML = '';
//   curriculums.forEach(c => {
//     const card = document.createElement('div');
//     card.className = 'curriculum-card';
//     card.innerHTML = `
//       <p><strong>Curriculum code:</strong> ${c.curriculumCode || ''}</p>
//       <p><strong>Name curriculum:</strong> ${c.nameCurriculum || ''}</p>
//       <p><strong>Lecturer:</strong> ${c.lecturer || ''}</p>
//       <p><strong>Description:</strong> ${c.description || ''}</p>
//       <p><strong>Department:</strong> ${c.department || ''}</p>
//       <p><strong>Subject:</strong> ${c.subject || ''}</p>
//       <p><strong>Branch:</strong> ${c.branch || ''}</p>
//       <button class="detail-btn" onclick="viewCurriculum('${c.curriculumCode}')">Detail</button>
//       <button class="download-btn" onclick="downloadCurriculum('${c.curriculumCode}')">Download</button>
//     `;
//     list.appendChild(card);
//   });
// }

// function updatePagination(totalPages, totalElements) {
//   const prevBtn = document.getElementById('prevBtn');
//   const nextBtn = document.getElementById('nextBtn');
//   const pageInfo = document.getElementById('pageInfo');

//   prevBtn.disabled = currentPage === 1;
//   nextBtn.disabled = currentPage === totalPages;
//   pageInfo.textContent = `Page ${currentPage} of ${totalPages} (Total: ${totalElements} items)`;
// }

// function prevPage() {
//   if (currentPage > 1) {
//     currentPage--;
//     loadCurriculums();
//   }
// }

// function nextPage() {
//   currentPage++;
//   loadCurriculums();
// }

// function filterCurriculums() {
//   const searchTerm = document.getElementById('searchInput').value.toLowerCase();
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/filter?name=${searchTerm}&lecturer=${searchTerm}&subject=${searchTerm}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     displayCurriculums(data);
//     updatePagination(1, data.length);
//     currentPage = 1;
//   })
//   .catch(err => {
//     console.error('Error filtering curriculums:', err);
//     alert('Failed to filter curriculums. Check console for details.');
//   });
// }

// function viewCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     document.getElementById('detailCode').textContent = data.curriculumCode || '';
//     document.getElementById('detailName').textContent = data.nameCurriculum || '';
//     document.getElementById('detailLecturer').textContent = data.lecturer || '';
//     document.getElementById('detailSubject').textContent = data.subject || '';
//     document.getElementById('detailBranch').textContent = data.branch || '';
//     document.getElementById('detailDepartment').textContent = data.department || '';
//     document.getElementById('detailDescription').textContent = data.description || '';
//     const urlLink = document.getElementById('detailUrlLink');
//     if (data.url) {
//       urlLink.href = data.url;
//       urlLink.style.display = 'inline';
//       document.getElementById('detailUrl').style.display = 'block';
//     } else {
//       document.getElementById('detailUrl').textContent = 'No file available';
//       urlLink.style.display = 'none';
//     }
//     document.getElementById('detailsContainer').style.display = 'block';
//   })
//   .catch(err => {
//     console.error('Error loading curriculum details:', err);
//     alert('Failed to load curriculum details. Check console for details.');
//   });
// }

// function downloadCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     if (data.url) {
//       window.open(data.url, '_blank');
//     } else {
//       alert('No file available for download.');
//     }
//   })
//   .catch(err => {
//     console.error('Error downloading curriculum:', err);
//     alert('Failed to download curriculum. Check console for details.');
//   });
// }

// function hideDetails() {
//   document.getElementById('detailsContainer').style.display = 'none';
// }

// function downloadFile() {
//   const url = document.getElementById('detailUrlLink').href;
//   if (url && url !== '#') {
//     window.open(url, '_blank');
//   } else {
//     alert('No file available for download.');
//   }
// }

// function goToUserDetails() {
//   if (token) {
//     loadUserInfo()
//       .then(userData => {
//         if (userData) {
//           localStorage.setItem('userData', JSON.stringify(userData));
//           window.location.href = 'user_details.html';
//         } else {
//           alert('Failed to load user info. Redirecting to login.');
//           window.location.href = 'login.html';
//         }
//       })
//       .catch(err => {
//         console.error('Error loading user info:', err);
//         alert('Failed to load user info due to server error. Please try again or log in.');
//         window.location.href = 'user_details.html'; // Chuyển trang để thử lại từ đó
//       });
//   } else {
//     alert('Please log in to view user details.');
//     window.location.href = 'login.html';
//   }
// }

// function loadUserInfo() {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   return fetch(`${API_BASE.replace('/api/api', '/api/api')}/user/me`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401 || res.status === 403) {
//       alert('Session expired or insufficient permissions. Please log in again.');
//       window.location.href = 'login.html';
//       return null;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     if (data) {
//       return {
//         idAccount: data.account.id,
//         name: data.name,
//         position: data.position,
//         identifier: data.identifier,
//         department: data.department,
//         idSchool: data.school?.id
//       };
//     }
//     return null;
//   });
// }









// const API_BASE = 'http://localhost:8080/api/api'; // Điều chỉnh URL backend của bạn
// const token = localStorage.getItem('jwt');
// let currentPage = 1;
// const itemsPerPage = 6;

// // Debounce utility function
// function debounce(func, delay) {
//   let timeoutId;
//   return function (...args) {
//     clearTimeout(timeoutId);
//     timeoutId = setTimeout(() => func.apply(this, args), delay);
//   };
// }

// // Debounced search function
// const debouncedFilter = debounce((searchTerm) => {
//   if (searchTerm.length < 2) {
//     alert('Please enter at least 2 characters to search.');
//     return;
//   }
//   currentPage = 1;
//   searchCurriculums(searchTerm);
// }, 300);

// document.addEventListener('DOMContentLoaded', () => {
//   if (!token) {
//     alert('Please log in to view curriculums.');
//     window.location.href = 'login.html';
//     return;
//   }
//   console.log('DOM loaded, initiating curriculum load...');
//   loadCurriculums();
// });

// function loadCurriculums() {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   console.log(`Fetching curriculums from ${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`);
//   fetch(`${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status} - ${res.statusText}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     console.log('Curriculums data received:', data);
//     displayCurriculums(data.content || data);
//     updatePagination(data.totalPages || 1, data.totalElements || data.length);
//   })
//   .catch(err => {
//     console.error('Error loading curriculums:', err);
//     alert('Failed to load curriculums. Check console for details.');
//   });
// }

// function displayCurriculums(curriculums) {
//   const list = document.getElementById('curriculumList');
//   list.innerHTML = '';
//   if (!curriculums || curriculums.length === 0) {
//     list.innerHTML = '<p>No curriculums found.</p>';
//     return;
//   }
//   curriculums.forEach(c => {
//     const card = document.createElement('div');
//     card.className = 'curriculum-card';
//     card.innerHTML = `
//       <p><strong>Curriculum code:</strong> ${c.curriculumCode || ''}</p>
//       <p><strong>Name curriculum:</strong> ${c.nameCurriculum || ''}</p>
//       <p><strong>Lecturer:</strong> ${c.lecturer || ''}</p>
//       <p><strong>Description:</strong> ${c.description || ''}</p>
//       <p><strong>Department:</strong> ${c.department || ''}</p>
//       <p><strong>Subject:</strong> ${c.subject || ''}</p>
//       <p><strong>Branch:</strong> ${c.branch || ''}</p>
//       <button class="detail-btn" onclick="viewCurriculum('${c.curriculumCode}')">Detail</button>
//       <button class="download-btn" onclick="downloadCurriculum('${c.curriculumCode}')">Download</button>
//     `;
//     list.appendChild(card);
//   });
// }

// function updatePagination(totalPages, totalElements) {
//   const prevBtn = document.getElementById('prevBtn');
//   const nextBtn = document.getElementById('nextBtn');
//   const pageInfo = document.getElementById('pageInfo');

//   prevBtn.disabled = currentPage === 1;
//   nextBtn.disabled = currentPage === totalPages;
//   pageInfo.textContent = `Page ${currentPage} of ${totalPages} (Total: ${totalElements} items)`;
//   console.log(`Pagination updated: Page ${currentPage} of ${totalPages}`);
// }

// function prevPage() {
//   if (currentPage > 1) {
//     currentPage--;
//     const searchTerm = document.getElementById('searchInput').value.trim();
//     if (searchTerm) {
//       searchCurriculums(searchTerm);
//     } else {
//       loadCurriculums();
//     }
//   }
// }

// function nextPage() {
//   currentPage++;
//   const searchTerm = document.getElementById('searchInput').value.trim();
//   if (searchTerm) {
//     searchCurriculums(searchTerm);
//   } else {
//     loadCurriculums();
//   }
// }

// function searchCurriculums(searchTerm) {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   console.log(`Searching curriculums with term: ${searchTerm}, page: ${currentPage}, size: ${itemsPerPage}`);
//   fetch(`${API_BASE}/curriculums/search?name=${encodeURIComponent(searchTerm)}&lecturer=${encodeURIComponent(searchTerm)}&subject=${encodeURIComponent(searchTerm)}&page=${currentPage}&size=${itemsPerPage}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status} - ${res.statusText}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     console.log('Search response:', data);
//     displayCurriculums(data.content || data);
//     updatePagination(data.totalPages || 1, data.totalElements || data.length);
//   })
//   .catch(err => {
//     console.error('Error searching curriculums:', err);
//     alert('Failed to search curriculums. Check console for details. Status: ' + err.message);
//   });
// }

// function filterCurriculums() {
//   const searchTerm = document.getElementById('searchInput').value.trim();
//   if (searchTerm === '') {
//     currentPage = 1;
//     loadCurriculums();
//     return;
//   }
//   debouncedFilter(searchTerm); // Sử dụng debounce thay vì gọi trực tiếp
// }

// function viewCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     document.getElementById('detailCode').textContent = data.curriculumCode || '';
//     document.getElementById('detailName').textContent = data.nameCurriculum || '';
//     document.getElementById('detailLecturer').textContent = data.lecturer || '';
//     document.getElementById('detailSubject').textContent = data.subject || '';
//     document.getElementById('detailBranch').textContent = data.branch || '';
//     document.getElementById('detailDepartment').textContent = data.department || '';
//     document.getElementById('detailDescription').textContent = data.description || '';
//     const urlLink = document.getElementById('detailUrlLink');
//     if (data.url) {
//       urlLink.href = data.url;
//       urlLink.style.display = 'inline';
//       document.getElementById('detailUrl').style.display = 'block';
//     } else {
//       document.getElementById('detailUrl').textContent = 'No file available';
//       urlLink.style.display = 'none';
//     }
//     document.getElementById('detailsContainer').style.display = 'block';
//   })
//   .catch(err => {
//     console.error('Error loading curriculum details:', err);
//     alert('Failed to load curriculum details. Check console for details.');
//   });
// }

// function downloadCurriculum(code) {
//   const headers = {
//     'Authorization': `Bearer ${token}`
//   };

//   fetch(`${API_BASE}/curriculums/get/${code}`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401) {
//       alert('Session expired. Please log in again.');
//       window.location.href = 'login.html';
//       return;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     if (data.url) {
//       window.open(data.url, '_blank');
//     } else {
//       alert('No file available for download.');
//     }
//   })
//   .catch(err => {
//     console.error('Error downloading curriculum:', err);
//     alert('Failed to download curriculum. Check console for details.');
//   });
// }

// function hideDetails() {
//   document.getElementById('detailsContainer').style.display = 'none';
// }

// function downloadFile() {
//   const url = document.getElementById('detailUrlLink').href;
//   if (url && url !== '#') {
//     window.open(url, '_blank');
//   } else {
//     alert('No file available for download.');
//   }
// }

// function goToUserDetails() {
//   if (token) {
//     loadUserInfo()
//       .then(userData => {
//         if (userData) {
//           localStorage.setItem('userData', JSON.stringify(userData));
//           window.location.href = 'user_details.html';
//         } else {
//           alert('Failed to load user info. Redirecting to login.');
//           window.location.href = 'login.html';
//         }
//       })
//       .catch(err => {
//         console.error('Error loading user info:', err);
//         alert('Failed to load user info due to server error. Please try again or log in.');
//         window.location.href = 'user_details.html';
//       });
//   } else {
//     alert('Please log in to view user details.');
//     window.location.href = 'login.html';
//   }
// }

// function loadUserInfo() {
//   const headers = {
//     'Authorization': `Bearer ${token}`,
//     'Content-Type': 'application/json'
//   };

//   return fetch(`${API_BASE}/user/me`, {
//     method: 'GET',
//     headers: headers
//   })
//   .then(res => {
//     if (res.status === 401 || res.status === 403) {
//       alert('Session expired or insufficient permissions. Please log in again.');
//       window.location.href = 'login.html';
//       return null;
//     }
//     if (!res.ok) {
//       throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     return res.json();
//   })
//   .then(data => {
//     if (data) {
//       return {
//         idAccount: data.account.id,
//         name: data.name,
//         position: data.position,
//         identifier: data.identifier,
//         department: data.department,
//         idSchool: data.school?.id
//       };
//     }
//     return null;
//   });
// }












// index.js
const API_BASE = 'http://localhost:8080/api/api'; // Adjust your backend URL
const token = localStorage.getItem('jwt');
let currentPage = 1;
const itemsPerPage = 6;

// Debounce utility function
function debounce(func, delay) {
  let timeoutId;
  return function (...args) {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => func.apply(this, args), delay);
  };
}

// Debounced search function
const debouncedFilter = debounce((searchTerm) => {
  if (searchTerm.length < 2) {
    alert('Please enter at least 2 characters to search.');
    return;
  }
  currentPage = 1;
  searchCurriculums(searchTerm);
}, 300);

document.addEventListener('DOMContentLoaded', () => {
  if (!token) {
    alert('Please log in to view curriculums.');
    window.location.href = 'login.html';
    return;
  }
  console.log('DOM loaded, initiating curriculum load...');
  loadCurriculums();
});

function loadCurriculums() {
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  console.log(`Fetching curriculums from ${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`);
  fetch(`${API_BASE}/curriculums/get?page=${currentPage}&size=${itemsPerPage}`, {
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
    console.log('Curriculums data received:', data);
    displayCurriculums(data.content || data);
    updatePagination(data.totalPages || 1, data.totalElements || data.length);
  })
  .catch(err => {
    console.error('Error loading curriculums:', err);
    alert('Failed to load curriculums. Check console for details.');
  });
}

function displayCurriculums(curriculums) {
  const list = document.getElementById('curriculumList');
  list.innerHTML = '';
  if (!curriculums || curriculums.length === 0) {
    list.innerHTML = '<p>No curriculums found.</p>';
    return;
  }
  curriculums.forEach(c => {
    const card = document.createElement('div');
    card.className = 'curriculum-card';
    card.innerHTML = `
      <p><strong>Curriculum code:</strong> ${c.curriculumCode || ''}</p>
      <p><strong>Name curriculum:</strong> ${c.nameCurriculum || ''}</p>
      <p><strong>Lecturer:</strong> ${c.lecturer || ''}</p>
      <button class="detail-btn" onclick="viewCurriculum('${c.curriculumCode}')">Detail</button>
      <button class="download-btn" onclick="downloadCurriculum('${c.curriculumCode}')">Download</button>
    `;
    list.appendChild(card);
  });
}

function updatePagination(totalPages, totalElements) {
  const prevBtn = document.getElementById('prevBtn');
  const nextBtn = document.getElementById('nextBtn');
  const pageInfo = document.getElementById('pageInfo');

  prevBtn.disabled = currentPage === 1;
  nextBtn.disabled = currentPage === totalPages;
  pageInfo.textContent = `Page ${currentPage} of ${totalPages} (Total: ${totalElements} items)`;
  console.log(`Pagination updated: Page ${currentPage} of ${totalPages}`);
}

function prevPage() {
  if (currentPage > 1) {
    currentPage--;
    const searchTerm = document.getElementById('searchInput').value.trim();
    if (searchTerm) {
      searchCurriculums(searchTerm);
    } else {
      loadCurriculums();
    }
  }
}

function nextPage() {
  currentPage++;
  const searchTerm = document.getElementById('searchInput').value.trim();
  if (searchTerm) {
    searchCurriculums(searchTerm);
  } else {
    loadCurriculums();
  }
}

function searchCurriculums(searchTerm) {
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  console.log(`Searching curriculums with term: ${searchTerm}`);
  fetch(`${API_BASE}/curriculums/filter?name=${encodeURIComponent(searchTerm)}&lecturer=${encodeURIComponent(searchTerm)}&identifier=${encodeURIComponent(searchTerm)}&subject=${encodeURIComponent(searchTerm)}&branch=${encodeURIComponent(searchTerm)}&department=${encodeURIComponent(searchTerm)}`, {
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
    console.log('Search response:', data);
    displayCurriculums(data); 
    updatePagination(1, data.length); 
  })
  .catch(err => {
    console.error('Error searching curriculums:', err);
    alert('Failed to search curriculums. Check console for details. Status: ' + err.message);
  });
}

function filterCurriculums() {
  const searchTerm = document.getElementById('searchInput').value.trim();
  if (searchTerm.length < 2) {
    alert('Please enter at least 2 characters to search.');
    return;
  }
  currentPage = 1;
  searchCurriculums(searchTerm);
}

function viewCurriculum(code) {
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
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
    return res.json();
  })
  .then(data => {
    document.getElementById('detailCode').textContent = data.curriculumCode || '';
    document.getElementById('detailName').textContent = data.nameCurriculum || '';
    document.getElementById('detailLecturer').textContent = data.lecturer || '';
    document.getElementById('detailSubject').textContent = data.subject || '';
    document.getElementById('detailBranch').textContent = data.branch || '';
    document.getElementById('detailDepartment').textContent = data.department || '';
    document.getElementById('detailDescription').textContent = data.description || '';
    const urlLink = document.getElementById('detailUrlLink');
    if (data.url) {
      urlLink.href = data.url;
      urlLink.style.display = 'inline';
      document.getElementById('detailUrl').style.display = 'block';
    } else {
      document.getElementById('detailUrl').textContent = 'No file available';
      urlLink.style.display = 'none';
    }  
    document.getElementById('modalOverlay').style.display = 'block';
    document.getElementById('detailsModal').style.display = 'block';
  })
  .catch(err => {
    console.error('Error loading curriculum details:', err);
    alert('Failed to load curriculum details. Check console for details.');
  });
}

function downloadCurriculum(code) {
  const headers = {
    'Authorization': `Bearer ${token}`
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
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
    return res.json();
  })
  .then(data => {
    if (data.url) {
      // Chuyển URL Cloudinary sang dạng tải về
      const downloadUrl = data.url.replace('/upload/', '/upload/fl_attachment/');
      
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.download = ''; // có thể đặt tên tại đây nếu muốn
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      alert('No file available for download.');
    }
  })
  .catch(err => {
    console.error('Error downloading curriculum:', err);
    alert('Failed to download curriculum. Check console for details.');
  });
}

function hideDetails() {
  document.getElementById('modalOverlay').style.display = 'none';
  document.getElementById('detailsModal').style.display = 'none';
}

function downloadFile() {
  const url = document.getElementById('detailUrlLink').href;
  if (url && url !== '#') {
    window.open(url, '_blank');
  } else {
    alert('No file available for download.');
  }
}

function goToUserDetails() {
  if (token) {
    loadUserInfo()
      .then(userData => {
        if (userData) {
          localStorage.setItem('userData', JSON.stringify(userData));
          window.location.href = 'user_details.html';
        } else {
          alert('Failed to load user info. Redirecting to login.');
          window.location.href = 'login.html';
        }
      })
      .catch(err => {
        console.error('Error loading user info:', err);
        alert('Failed to load user info due to server error. Please try again or log in.');
        window.location.href = 'user_details.html';
      });
  } else {
    alert('Please log in to view user details.');
    window.location.href = 'login.html';
  }
}

function loadUserInfo() {
  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  return fetch(`${API_BASE}/user/me`, {
    method: 'GET',
    headers: headers
  })
  .then(res => {
    if (res.status === 401 || res.status === 403) {
      alert('Session expired or insufficient permissions. Please log in again.');
      window.location.href = 'login.html';
      return null;
    }
    if (!res.ok) {
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
    return res.json();
  })
  .then(data => {
    if (data) {
      return {
        idAccount: data.account.id,
        name: data.name,
        position: data.position,
        identifier: data.identifier,
        department: data.department,
        idSchool: data.school?.id
      };
    }
    return null;
  });
}

function goToLogin() {
  localStorage.removeItem('jwt'); 
  window.location.href = 'login.html';
}

function toggleChatbot() {
  const chatbot = document.getElementById('chatbotContainer');
  const isVisible = chatbot.style.display === 'block';
  chatbot.style.display = isVisible ? 'none' : 'block';
}
