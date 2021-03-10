fun getAggregatorById(orderTypeId: Int): String? {
            try {
                return onlineAggregators.filterValues { it == orderTypeId }.keys.first()
            } catch (error: NoSuchElementException) {
                error.printStackTrace()
            } catch (error: Exception) {
                error.printStackTrace()
            }
            return null
        }