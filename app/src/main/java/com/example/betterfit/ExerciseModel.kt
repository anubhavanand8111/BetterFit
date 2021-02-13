package com.example.betterfit

class ExerciseModel(var id:Int,
                    var name:String,
                    var image:Int,
                    var isCompleted:Boolean,
                    var isSelected:Boolean) {


    @JvmName("getId1")
    fun getId():Int{
        return id
    }
    @JvmName("setId1")
    fun setId(id:Int){
        this.id=id
    }
    @JvmName("getName1")
    fun getName():String{
        return name
    }
    @JvmName("setName1")
    fun setName(name:String){
        this.name=name

    }
    @JvmName("getImage1")
    fun getImage():Int{
        return image
    }

    @JvmName("setImage1")
    fun setImage(image:Int){
        this.image=image
    }

    fun getIsCompleted():Boolean{
    return isCompleted
    }

    fun setIsCompleted(isCompleted:Boolean){
        this.isCompleted=isCompleted
    }

    fun getIsSelected():Boolean{
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected=isSelected
    }

}