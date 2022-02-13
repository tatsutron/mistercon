const handhelds = [
  {
    core: "AtariLynx",
    folder: "AtariLynx",
    format: [
      {
        extension: "lnx",
        headerSizeInBytes: 64,
        mbcCommand: "ATARILYNX",
      },
    ],
    image: require("../assets/atari_lynx.png"),
    name: "Atari Lynx",
  },
  {
    core: "Gameboy",
    folder: "Gameboy",
    format: [
      {
        extension: "gb",
        mbcCommand: "GAMEBOY",
      },
    ],
    image: require("../assets/game_boy.png"),
    name: "Game Boy",
  },
  {
    core: "GBA",
    folder: "GBA",
    format: [
      {
        extension: "gba",
        mbcCommand: "GBA",
      },
    ],
    image: require("../assets/game_boy_advance.png"),
    name: "Game Boy Advance",
  },
  {
    core: "Gameboy",
    folder: "Gameboy",
    format: [
      {
        extension: "gbc",
        mbcCommand: "GAMEBOY.COL",
      },
    ],
    image: require("../assets/game_boy_color.png"),
    name: "Game Boy Color",
  },
  {
    core: "SMS",
    folder: "SMS",
    format: [
      {
        extension: "gg",
        mbcCommand: "SMS.GG",
      },
    ],
    image: require("../assets/game_gear.png"),
    name: "Game Gear",
  },
  {
    core: "WonderSwan",
    folder: "WonderSwan",
    format: [
      {
        extension: "ws",
        mbcCommand: "WONDERSWAN",
      },
    ],
    image: require("../assets/wonderswan.png"),
    name: "WonderSwan",
  },
  {
    core: "WonderSwan",
    folder: "WonderSwan",
    format: [
      {
        extension: "wsc",
        mbcCommand: "WONDERSWAN.COL",
      },
    ],
    image: require("../assets/wonderswan_color.png"),
    name: "WonderSwan Color",
  },
];

///////////////////////////////////////////////////////////////////////////////
export default handhelds;
