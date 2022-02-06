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
export default {
  getExtension,
  getFilename,
  loadGame,
};
