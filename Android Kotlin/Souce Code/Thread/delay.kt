Many Ways
1. Using Handler class
Handler().postDelayed({
    TODO("Do something")
    }, 2000)
2. Using Timer class
Timer().schedule(object : TimerTask() {
    override fun run() {
        TODO("Do something")
    }
}, 2000)
Shorter

Timer().schedule(timerTask {
    TODO("Do something")
}, 2000)
Shortest

Timer().schedule(2000) {
    TODO("Do something")
}
3. Using Executors class
Executors.newSingleThreadScheduledExecutor().schedule({
    TODO("Do something")
}, 2, TimeUnit.SECONDS)