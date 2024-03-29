const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;

async function register(email, password, repeatPassword) {
  try {
    const user = await fetch(REACT_APP_BASE_URL + '/users/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ email, password, repeatPassword })
    });
    return user.json();
  } catch (error) {
    throw new Error(error)
  }
}

async function login(email, password) {
  try {
    const user = await fetch(REACT_APP_BASE_URL + '/users/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ email, password })
    });
    return user.json();
  } catch (error) {
    throw new Error(error)
  }
}

async function logout() {
  try {
    const response = await fetch(REACT_APP_BASE_URL + '/users/logout', {
      method: 'GET',
      credentials: 'include',
    });
    return response;
  } catch (error) {
    throw new Error(error)
  }
}

async function changeUserData(userId, userData) {
  try {
    const response = await fetch(REACT_APP_BASE_URL + `/users/${userId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include',
      body: JSON.stringify(userData)
    });
    if (!response.ok) {
      return response;
    }
    return response.json();
  } catch (error) {
    throw new Error(error);
  }
}

async function getAllUsers() {
  try {
    const response = await fetch(REACT_APP_BASE_URL + `/users/admin`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include'
    });
    if (!response.ok) {
      return response;
    }
    return response.json();
  } catch (error) {
    throw new Error(error);
  }
}

async function makeAdmin(userId) {
  try {
    const response = await fetch(REACT_APP_BASE_URL + `/users/admin/${userId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include'
    });
    if (!response.ok) {
      return response;
    }
    return response.json();
  } catch (error) {
    throw new Error(error);
  }
}

export { register, login, logout, changeUserData, getAllUsers, makeAdmin }