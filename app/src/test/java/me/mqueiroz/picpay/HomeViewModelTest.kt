package me.mqueiroz.picpay

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.ui.home.HomeFragmentState
import me.mqueiroz.picpay.ui.home.HomeViewModel
import me.mqueiroz.picpay.utils.schedulers.TrampolineSchedulerProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mock<Repository>()

    private val schedulerProvider = TrampolineSchedulerProvider()

    private val viewModel = HomeViewModel(repository, schedulerProvider)

    private val stateObserver = mock<Observer<HomeFragmentState>>()

    @Before
    fun setUp() {
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun loadUsers_ShouldSetLoadedState_onNext() {
        // given
        val users = listOf(
            User("User1"),
            User("User2"),
            User("User3"),
            User("User4"),
            User("User5")
        )
        `when`(repository.getUsers()).thenReturn(Observable.just(users))

        // when
        viewModel.loadUsers()

        //
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(HomeFragmentState.Loading)
            verify(stateObserver).onChanged(HomeFragmentState.Loaded(users))
            verifyNoMoreInteractions(stateObserver)
        }
    }

    @Test
    fun loadUsers_ShouldSetErrorState_onError() {
        // given
        val exception = RuntimeException("Test")
        `when`(repository.getUsers()).thenReturn(Observable.error(exception))

        // when
        viewModel.loadUsers()

        // then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(HomeFragmentState.Loading)
            verify(stateObserver).onChanged(HomeFragmentState.Error())
            verifyNoMoreInteractions(stateObserver)
        }
    }

    @After
    fun tearDown() {
        viewModel.state.removeObserver(stateObserver)
    }
}