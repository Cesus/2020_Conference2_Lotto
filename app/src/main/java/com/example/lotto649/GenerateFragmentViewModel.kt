package com.example.lotto649

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GenerateFragmentViewModel : ViewModel() {

    // The following are live data encapsulations to display or relay data from viewmodel to fragment or xml
    private val _userLottoNum = MutableLiveData<String>()
    val userLottoNum: LiveData<String>
        get() = _userLottoNum

    private val _winningLottoNum = MutableLiveData<String>()
    val winningLottoNum: LiveData<String>
        get() = _winningLottoNum

    private val _matchedPicks = MutableLiveData<String>()
    val matchedPicks: LiveData<String>
        get() = _matchedPicks

    private var _lotteryFinish = MutableLiveData<Boolean>()
    val lotteryFinish: LiveData<Boolean>
        get() = _lotteryFinish

    // I was unable to get the length of matchedPicks, so this variable is used instead
    var wins = 0

    // Main function
    fun getLottery() {
        // reset wins
        wins = 0
        // generate a set of 6 random numbers for the user's ticket and the winning ticket
        val userNum = getLotteryNumbers()
        val winNum = getLotteryNumbers()

        // if any items in the sets match, add those to the matched set
        val matchedNum: MutableSet<Any> = mutableSetOf()
        userNum.forEach { c ->
            if (c in winNum) {
                wins++
                matchedNum.add(c)
            }
        }

        _lotteryFinish.value = true

        // sets the values of livedata, removing the '[' and ',' from the sets
        _userLottoNum.value = userNum.toString().replace("[^A-Za-z0-9 ]".toRegex(),"")
        _winningLottoNum.value = winNum.toString().replace("[^A-Za-z0-9 ]".toRegex(),"")
        _matchedPicks.value = matchedNum.toString().replace("[^A-Za-z0-9 ]".toRegex(),"")

    }

    private fun getLotteryNumbers() : Set<Any> {

        // Initialize variables
        val random = Random()
        var ranInt: Int
        val totalNum = 6
        val pickedNum: MutableSet<Int> = mutableSetOf()

        // Generate a random number bounded between 1 and 49, if the same number appears again, loop continues until a new number is found
        fun random(from: Int, to: Int) {
            do{
                ranInt = random.nextInt(to - from) + from
            }while(pickedNum.contains(ranInt))

            pickedNum.add(ranInt)
        }

        // Ensures that are only a defined amount of numbers can be picked
        while(pickedNum.size < totalNum){
            random(1,49)
        }

        // return the sorted set
        return pickedNum.toSortedSet()
    }

}

