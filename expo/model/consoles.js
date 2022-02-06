const consoles = {
  atari2600: {
    core: "Atari2600",
    folder: "Atari2600",
    format: [
      {
        extension: "rom",
        mbcCommand: "ATARI2600",
      },
    ],
    name: "Atari 2600",
  },
  atari5200: {
    core: "Atari5200",
    folder: "Atari5200",
    format: [
      {
        extension: "rom",
        mbcCommand: "ATARI5200",
      },
    ],
    name: "Atari 5200",
  },
  atari7800: {
    core: "Atari7800",
    folder: "Atari7800",
    format: [
      {
        extension: "a78",
        headerSizeInBytes: 128,
        mbcCommand: "ATARI7800",
      },
    ],
    name: "Atari 7800",
  },
  atariLynx: {
    core: "AtariLynx",
    folder: "AtariLynx",
    format: [
      {
        extension: "lnx",
        headerSizeInBytes: 64,
        mbcCommand: "ATARILYNX",
      },
    ],
    name: "Atari Lynx",
  },
  ballyAstrocade: {
    core: "Astrocade",
    folder: "Astrocade",
    format: [
      {
        extension: "bin",
        mbcCommand: "ASTROCADE",
      },
    ],
    name: "Bally Astrocade",
  },
  colecoVision: {
    core: "ColecoVision",
    folder: "Coleco",
    format: [
      {
        extension: "col",
        mbcCommand: "COLECO",
      },
    ],
    name: "ColecoVision",
  },
  famicomDiskSystem: {
    core: "NES",
    folder: "NES",
    format: [
      {
        extension: "fds",
        headerSizeInBytes: 16,
        mbcCommand: "NES.FDS",
      },
    ],
    name: "Famicom Disk System",
  },
  gameBoy: {
    core: "Gameboy",
    folder: "Gameboy",
    format: [
      {
        extension: "gb",
        mbcCommand: "GAMEBOY",
      },
    ],
    name: "Game Boy",
  },
  gameBoyAdvance: {
    core: "GBA",
    folder: "GBA",
    format: [
      {
        extension: "gba",
        mbcCommand: "GBA",
      },
    ],
    name: "Game Boy Advance",
  },
  gameBoyColor: {
    core: "Gameboy",
    folder: "Gameboy",
    format: [
      {
        extension: "gbc",
        mbcCommand: "GAMEBOY.COL",
      },
    ],
    name: "Game Boy Color",
  },
  gameGear: {
    core: "SMS",
    folder: "SMS",
    format: [
      {
        extension: "gg",
        mbcCommand: "SMS.GG",
      },
    ],
    name: "Game Gear",
  },
  intellivision: {
    core: "Intellivision",
    folder: "Intellivision",
    format: [
      {
        extension: "bin",
        mbcCommand: "INTELLIVISION",
      },
    ],
    name: "Intellivision",
  },
  intertonVc4000: {
    core: "VC4000",
    folder: "VC4000",
    format: [
      {
        extension: "bin",
        mbcCommand: "VC4000",
      },
    ],
    name: "Interton VC 4000",
  },
  masterSystem: {
    core: "SMS",
    folder: "SMS",
    format: [
      {
        extension: "sms",
        mbcCommand: "SMS",
      },
    ],
    name: "Master System",
  },
  neoGeo: {
    core: "NeoGeo",
    folder: "NeoGeo",
    format: [
      {
        extension: "neo",
        mbcCommand: "NEOGEO",
      },
    ],
    name: "Neo Geo",
  },
  nintendoEntertainmentSystem: {
    core: "NES",
    folder: "NES",
    format: [
      {
        extension: "nes",
        headerSizeInBytes: 16,
        mbcCommand: "NES",
      },
    ],
    name: "Nintendo Entertainment System",
  },
  odyssey2: {
    core: "Odyssey2",
    folder: "Odyssey2",
    format: [
      {
        extension: "bin",
        mbcCommand: "ODYSSEY2",
      },
    ],
    name: "Odyssey 2",
  },
  segaCd: {
    core: "MegaCD",
    folder: "MegaCD",
    format: [
      {
        extension: "chd",
        mbcCommand: "MEGACD",
      },
    ],
    name: "Sega CD",
  },
  segaGenesis: {
    core: "Genesis",
    folder: "Genesis",
    format: [
      {
        extension: "bin",
        mbcCommand: "MEGADRIVE.BIN",
      },
      {
        extension: "gen",
        mbcCommand: "GENESIS",
      },
      {
        extension: "md",
        mbcCommand: "MEGADRIVE",
      },
    ],
    name: "Sega Genesis",
  },
  sg1000: {
    core: "ColecoVision",
    folder: "Coleco",
    format: [
      {
        extension: "sg",
        mbcCommand: "COLECO.SG",
      },
    ],
    name: "SG-1000",
  },
  superGrafx: {
    core: "TurboGrafx16",
    folder: "TGFX16",
    format: [
      {
        extension: "sgx",
        mbcCommand: "SUPERGRAFX",
      },
    ],
    name: "SuperGrafx",
  },
  superNintendo: {
    core: "SNES",
    folder: "SNES",
    format: [
      {
        extension: "sfc",
        mbcCommand: "SNES",
      },
      {
        extension: "smc",
        mbcCommand: "SNES",
      },
    ],
    name: "Super Nintendo",
  },
  turboGrafx16: {
    core: "TurboGrafx16",
    folder: "TGFX16",
    format: [
      {
        extension: "pce",
        mbcCommand: "TGFX16",
      },
    ],
    name: "TurboGrafx-16",
  },
  turboGrafxCd: {
    core: "TurboGrafx16",
    folder: "TGFX16-CD",
    format: [
      {
        extension: "chd",
        mbcCommand: "TGFX16-CD",
      },
    ],
    name: "TurboGrafx-CD",
  },
  vectrex: {
    core: "Vectrex",
    folder: "Vectrex",
    format: [
      {
        extension: "ovr",
        mbcCommand: "VECTREX.OVR",
      },
      {
        extension: "vec",
        mbcCommand: "VECTREX",
      },
    ],
    name: "Vectrex",
  },
  wonderSwan: {
    core: "WonderSwan",
    folder: "WonderSwan",
    format: [
      {
        extension: "ws",
        mbcCommand: "WONDERSWAN",
      },
    ],
    name: "WonderSwan",
  },
  wonderSwanColor: {
    core: "WonderSwan",
    folder: "WonderSwan",
    format: [
      {
        extension: "wsc",
        mbcCommand: "WONDERSWAN.COL",
      },
    ],
    name: "WonderSwan Color",
  },
};

///////////////////////////////////////////////////////////////////////////////
export default consoles;
