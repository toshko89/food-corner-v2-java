export const newQuery = (ids) => {
  return ids.map(id => encodeURIComponent('ids[]') + '=' + encodeURIComponent(id)).join('&');
};