package me.javagic.hitchhikerace.view.eventcreation.takecheckpoint

data class CheckPointCrew(
    val crewName: String,
    val timeHour: Int,
    val timeMinute: Int,
    val date: String
) {
    override fun toString(): String {
        return "$crewName $timeHour:$timeMinute $date"
    }
}