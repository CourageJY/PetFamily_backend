package com.pet.util.enums;

public enum PetState {
    canBeFoundBack,//能被寻回 :0
    canBeAdopted,//能被领养 :1
    waitingFindback,//已有寻回申请表被审核通过 :2
    waitingAdopted,//已有领养申请表被审核通过 :3
    findBack,//已被寻回 :4
    adopted,//已被领养 :5
    dead//死亡 :6
}
