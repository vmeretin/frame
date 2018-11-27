package io.github.kmeret.frame.demo.ui.following

import androidx.lifecycle.*
import io.github.kmeret.frame.demo.domain.entity.User
import io.github.kmeret.frame.demo.github.GithubConfig
import io.github.kmeret.frame.demo.github.GithubService
import io.github.kmeret.frame.demo.github.model.GithubUser
import io.github.kmeret.frame.lifecycle.DataEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FollowViewModel(githubService: GithubService) : ViewModel() {

    private val followingRx: Flowable<List<GithubUser>> =
            githubService.getFollowingListRx(GithubConfig.userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnRequest { loading.value = true }
                    .doOnNext { empty.value = it.isEmpty() }
                    .doOnError { error.value = Exception(it.message) }
                    .doOnComplete { loading.value = false }

    private val followersRx: Flowable<List<GithubUser>> =
            githubService.getFollowerListRx(GithubConfig.userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnRequest { loading.value = true }
                    .doOnNext { empty.value = it.isEmpty() }
                    .doOnError { error.value = Exception(it.message) }
                    .doOnComplete { loading.value = false }

    private val userList: MediatorLiveData<MutableList<GithubUser>> = MediatorLiveData()
    private val error: DataEvent<Exception> = DataEvent()
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val empty: DataEvent<Boolean> = DataEvent()

    fun requestUserList() {
        userList.apply {
            value = ArrayList()
            addSource(LiveDataReactiveStreams.fromPublisher(followingRx)) {
                value = value!!.apply { addAll(it) }
            }
            addSource(LiveDataReactiveStreams.fromPublisher(followersRx)) {
                value = value!!.apply { addAll(it) }
            }
        }
    }

    fun getUserList(): LiveData<List<User>> = Transformations.map(userList) { githubUserList ->
        githubUserList.map { it.map() }
    }
    fun getError(): LiveData<Exception> = error
    fun isLoading(): LiveData<Boolean> = loading
    fun isEmpty(): LiveData<Boolean> = empty

}