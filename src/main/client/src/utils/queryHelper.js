export const newQuery = (query) => {
  return Object.entries(query)
    .map(([k, v]) => {
      return `${k}=${v}`
    }).join('&');
};