package me.mqueiroz.picpay.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
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
            User("img_0", "name_0", 0, "username_0"),
            User("img_1", "name_1", 1, "username_1"),
            User("img_2", "name_2", 2, "username_2"),
            User("img_3", "name_3", 3, "username_3"),
            User("img_4", "name_4", 4, "username_4")
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