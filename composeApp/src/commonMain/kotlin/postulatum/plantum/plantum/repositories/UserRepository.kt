package postulatum.plantum.plantum.repositories

import postulatum.plantum.plantum.model.User

class UserRepository {
    companion object {
        private var _user: User? = null


        fun initUser(user : User) {
            _user = user
        }

        fun getUser() : User {
            if(_user == null) {
                throw Exception("User not initialized!");
            }
            return _user!!
        }

        fun isInitialized() : Boolean {
            return _user != null
        }

        fun clearUser() {
            _user = null
        }
    }
}
