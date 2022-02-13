const consoles = [
  {
    core: "Atari2600",
    folder: "Atari2600",
    format: [
      {
        extension: "rom",
        mbcCommand: "ATARI2600",
      },
    ],
    image: require("../assets/atari_2600.png"),
    name: "Atari 2600",
  },
  {
    core: "Atari5200",
    folder: "Atari5200",
    format: [
      {
        extension: "rom",
        mbcCommand: "ATARI5200",
      },
    ],
    image: require("../assets/atari_5200.png"),
    name: "Atari 5200",
  },
  {
    core: "Atari7800",
    folder: "Atari7800",
    format: [
      {
        extension: "a78",
        headerSizeInBytes: 128,
        mbcCommand: "ATARI7800",
      },
    ],
    image: require("../assets/atari_7800.png"),
    name: "Atari 7800",
  },
  {
    core: "Astrocade",
    folder: "Astrocade",
    format: [
      {
        extension: "bin",
        mbcCommand: "ASTROCADE",
      },
    ],
    image: require("../assets/bally_astrocade.png"),
    name: "Bally Astrocade",
  },
  {
    core: "ColecoVision",
    folder: "Coleco",
    format: [
      {
        extension: "col",
        mbcCommand: "COLECO",
      },
    ],
    image: require("../assets/colecovision.png"),
    name: "ColecoVision",
  },
  {
    core: "NES",
    folder: "NES",
    format: [
      {
        extension: "fds",
        headerSizeInBytes: 16,
        mbcCommand: "NES.FDS",
      },
    ],
    image: require("../assets/famicom_disk_system.png"),
    name: "Famicom Disk System",
  },
  {
    core: "Intellivision",
    folder: "Intellivision",
    format: [
      {
        extension: "bin",
        mbcCommand: "INTELLIVISION",
      },
    ],
    image: require("../assets/intellivision.png"),
    name: "Intellivision",
  },
  {
    core: "VC4000",
    folder: "VC4000",
    format: [
      {
        extension: "bin",
        mbcCommand: "VC4000",
      },
    ],
    image: require("../assets/interton_vc_4000.png"),
    name: "Interton VC 4000",
  },
  {
    core: "SMS",
    folder: "SMS",
    format: [
      {
        extension: "sms",
        mbcCommand: "SMS",
      },
    ],
    image: require("../assets/master_system.png"),
    name: "Master System",
  },
  {
    core: "NeoGeo",
    folder: "NeoGeo",
    format: [
      {
        extension: "neo",
        mbcCommand: "NEOGEO",
      },
    ],
    image: require("../assets/neo_geo.png"),
    name: "Neo Geo",
  },
  {
    core: "NES",
    folder: "NES",
    format: [
      {
        extension: "nes",
        headerSizeInBytes: 16,
        mbcCommand: "NES",
      },
    ],
    image: require("../assets/nintendo_entertainment_system.png"),
    name: "Nintendo Entertainment System",
  },
  {
    core: "Odyssey2",
    folder: "Odyssey2",
    format: [
      {
        extension: "bin",
        mbcCommand: "ODYSSEY2",
      },
    ],
    image: require("../assets/odyssey_2.png"),
    name: "Odyssey 2",
  },
  {
    core: "MegaCD",
    folder: "MegaCD",
    format: [
      {
        extension: "chd",
        mbcCommand: "MEGACD",
      },
    ],
    image: require("../assets/sega_cd.png"),
    name: "Sega CD",
  },
  {
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
    image: require("../assets/sega_genesis.png"),
    name: "Sega Genesis",
  },
  {
    core: "ColecoVision",
    folder: "Coleco",
    format: [
      {
        extension: "sg",
        mbcCommand: "COLECO.SG",
      },
    ],
    image: require("../assets/sg_1000.png"),
    name: "SG-1000",
  },
  {
    core: "TurboGrafx16",
    folder: "TGFX16",
    format: [
      {
        extension: "sgx",
        mbcCommand: "SUPERGRAFX",
      },
    ],
    image: require("../assets/super_grafx.png"),
    name: "SuperGrafx",
  },
  {
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
    image: require("../assets/super_nintendo.png"),
    name: "Super Nintendo",
  },
  {
    core: "TurboGrafx16",
    folder: "TGFX16",
    format: [
      {
        extension: "pce",
        mbcCommand: "TGFX16",
      },
    ],
    image: require("../assets/turbo_grafx_16.png"),
    name: "TurboGrafx-16",
  },
  {
    core: "TurboGrafx16",
    folder: "TGFX16-CD",
    format: [
      {
        extension: "chd",
        mbcCommand: "TGFX16-CD",
      },
    ],
    image: require("../assets/turbo_grafx_cd.png"),
    name: "TurboGrafx-CD",
  },
  {
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
    image: require("../assets/vectrex.png"),
    name: "Vectrex",
  },
];

///////////////////////////////////////////////////////////////////////////////
export default consoles;
