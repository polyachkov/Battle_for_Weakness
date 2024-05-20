export enum Turn {
  MY,
  OPPONENT,
}

export const idHandPictures: DynamicObject<string> = {
  1: '../../../../assets/Photoes/card/kobold.png',
  2: '../../../../assets/Photoes/card/sectarian.png',
  3: '../../../../assets/Photoes/card/dwarf.png',
  4: '../../../../assets/Photoes/card/rat.png',
  5: '../../../../assets/Photoes/card/mice.png',
  6: '../../../../assets/Photoes/card/goats.png',
  7: '../../../../assets/Photoes/card/stoneT.png',
  8: '../../../../assets/Photoes/card/lowest.png',
  9: '../../../../assets/Photoes/card/egorka.png',
  10: '../../../../assets/Photoes/card/proud.png',
  11: '../../../../assets/Photoes/card/stoneC.png',
  12: '../../../../assets/Photoes/card/rich.png',
};

export const idOppHandPictures: DynamicObject<string> = {
  1: '../../../../assets/Photoes/card/back/deck cover red.png',
};

export const idMoneyCollectorPictures: DynamicObject<string> = {
  0: '/',
  1: '../../../../assets/Photoes/hearth.png',
  2: '../../../../assets/Photoes/FieldPictures/yellowCircle.png',
};

export interface DynamicObject<T> {
  [key: string]: T;
}

export const field: any[][] = [
  [
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 7,
      health: 11,
      attackSpeed: 16,
      evasion: 0.3,
      moveSpeed: 5,
      cost: 4,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
  [
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 0,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 1,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
    {
      name: 2,
      attack: 6,
      health: 10,
      attackSpeed: 15,
      evasion: 0.2,
      moveSpeed: 4,
      cost: 3,
    },
  ],
];
