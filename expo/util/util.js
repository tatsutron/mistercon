const getExtension = ({ path }) => /(?:\.([^.]+))?$/.exec(path)[1];

///////////////////////////////////////////////////////////////////////////////
const getFileName = ({ path }) =>
  path
    .split("/")
    .pop()
    .replace(`.${getExtension({ path })}`, "");

///////////////////////////////////////////////////////////////////////////////
const getFolderName = ({ path }) =>
  path
    .split("/")
    .map((token) => {
      return token.match(/[^ ]+/g);
    })
    .filter((token) => {
      return token != null;
    })
    .pop();

///////////////////////////////////////////////////////////////////////////////
const isFolder = ({ path }) => path.endsWith("/");

///////////////////////////////////////////////////////////////////////////////
const loadGame = ({ console, path }) => {
  const { host, port } = require("../config");
  const command = console.format.find((format) => {
    return format.extension === getExtension({ path });
  }).mbcCommand;
  const url = `http://${host}:${port}/load/${command}/${path}`;
  try {
    fetch(url);
  } catch (error) {
    alert(error);
  }
};

///////////////////////////////////////////////////////////////////////////////
export default {
  getExtension,
  getFileName,
  getFolderName,
  isFolder,
  loadGame,
};
