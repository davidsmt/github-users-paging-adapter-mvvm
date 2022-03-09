package com.githubstarredusers.view.screens.githubusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.githubstarredusers.presentation.factory.ViewModelFactory
import com.githubstarredusers.presentation.viewmodels.GitHubUsersViewModel
import com.githubstarredusers.view.databinding.FragmentGithubUsersFragmentBinding
import com.githubstarredusers.view.di.ViewComponent
import com.githubstarredusers.view.di.ViewComponentFactoryProvider
import com.githubstarredusers.view.mappers.UiErrorsMapper
import com.githubstarredusers.view.screens.githubusers.adapters.PagingLoadStateAdapter
import com.githubstarredusers.view.screens.githubusers.adapters.UserAdapter
import com.githubstarredusers.view.utils.clearItemDecorations
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GitHubUsersFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GitHubUsersViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentGithubUsersFragmentBinding? = null
    private val binding: FragmentGithubUsersFragmentBinding
        get() = _binding!!

    private lateinit var usersAdapter: UserAdapter

    private fun getViewComponent(): ViewComponent =
        (requireContext().applicationContext as ViewComponentFactoryProvider).provideViewComponentFactory()
            .create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubUsersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        viewModel.start()
    }

    override fun onDestroyView() {
        binding.usersList.clearItemDecorations()
        _binding = null
        super.onDestroyView()
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun setupViews() {
        with(binding) {
            usersAdapter = UserAdapter { login ->
                findNavController().navigate(
                    GitHubUsersFragmentDirections.actionFragmentGithubUsersFragmentToGitHubUserProfileFragment(
                        login
                    )
                )
            }
            usersList.adapter = usersAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(usersAdapter),
                footer = PagingLoadStateAdapter(usersAdapter)
            )

            lifecycleScope.launchWhenCreated {
                usersAdapter.loadStateFlow.collectLatest { loadStates ->
                    binding.swipeRefreshContainer.isRefreshing =
                        loadStates.mediator?.refresh is LoadState.Loading
                }
            }

            lifecycleScope.launchWhenCreated {
                viewModel.uiState.collectLatest {
                    usersAdapter.submitData(it)
                }
            }

            lifecycleScope.launchWhenCreated {
                usersAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.Error }
                    .collectLatest { loadStates ->
                        if (loadStates.refresh is LoadState.Error) {
                            Toast.makeText(
                                requireContext(),
                                UiErrorsMapper(requireContext().resources).map((loadStates.refresh as LoadState.Error).error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshContainer.setOnRefreshListener {
            usersAdapter.refresh()
        }
    }

}