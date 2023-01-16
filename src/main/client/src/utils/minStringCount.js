export default function stringCount(string) {
  const stringArr = string.split(',').map(x => x.trim());
  return stringArr.filter(string => string !== '').length >= 3;
}