const TOKEN_KEY = "token";
const INFO = "health-info";
const ACTIVE_PATH = "active_key";
const USER_ID_KEY = "userId"; // 新增 userId 的存储键
export function getToken() {
  return sessionStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
  sessionStorage.setItem(TOKEN_KEY, token);
}

export function getHealthInfo() {
  return sessionStorage.getItem(INFO);
}

export function setHealthInfo(obj) {
  sessionStorage.setItem(INFO, obj);
}

export function clearToken() {
  sessionStorage.clear();
}
export function getActivePath() {
  return sessionStorage.getItem(ACTIVE_PATH);
}

export function setActivePath(path) {
  sessionStorage.setItem(ACTIVE_PATH, path);
}
// 新增设置 userId 的方法
export function setUserId(userId) {
  sessionStorage.setItem(USER_ID_KEY, userId);
}

// 新增获取 userId 的方法
export function getUserId() {
  return sessionStorage.getItem(USER_ID_KEY);
}