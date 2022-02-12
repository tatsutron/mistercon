const config = require("../config.json");

///////////////////////////////////////////////////////////////////////////////
const getExtension = ({ path }) => /(?:\.([^.]+))?$/.exec(path)[1];

///////////////////////////////////////////////////////////////////////////////
const getFilename = ({ path }) =>
  path
    .split("/")
    .pop()
    .replace(`.${getExtension({ path })}`, "");

///////////////////////////////////////////////////////////////////////////////
const loadGame = ({ console, path }) => {
  const host = config.host;
  const port = config.port;
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
const tokenizePath = ({ path }) =>
  path
    .split("/")
    .map((token) => {
      return token.match(/[^ ]+/g);
    })
    .filter((token) => {
      return token != null;
    });

///////////////////////////////////////////////////////////////////////////////
export default {
  getExtension,
  getFilename,
  loadGame,
  tokenizePath,
};
