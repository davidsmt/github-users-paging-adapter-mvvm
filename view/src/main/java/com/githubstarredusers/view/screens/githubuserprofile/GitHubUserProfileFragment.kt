package com.githubstarredusers.view.screens.githubuserprofile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubstarredusers.presentation.factory.ViewModelFactory
import com.githubstarredusers.presentation.models.GitHubUserProfileUiModel
import com.githubstarredusers.presentation.models.UiState
import com.githubstarredusers.presentation.viewmodels.GitHubUserProfileViewModel
import com.githubstarredusers.view.R
import com.githubstarredusers.view.databinding.FragmentGithubUserProfileFragmentBinding
import com.githubstarredusers.view.di.ViewComponent
import com.githubstarredusers.view.di.ViewComponentFactoryProvider
import com.githubstarredusers.view.mappers.UiErrorsMapper
import com.githubstarredusers.view.screens.githubuserprofile.adapters.RepositoryAdapter
import com.githubstarredusers.view.utils.SpaceItemDecoration
import com.githubstarredusers.view.utils.clearItemDecorations
import com.githubstarredusers.view.utils.visible
import javax.inject.Inject


class GitHubUserProfileFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val args: GitHubUserProfileFragmentArgs by navArgs()

    private val viewModel: GitHubUserProfileViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentGithubUserProfileFragmentBinding? = null
    private val binding: FragmentGithubUserProfileFragmentBinding
        get() = _binding!!

    private lateinit var topRepositoriesAdapter: RepositoryAdapter
    private lateinit var starredRepositoriesAdapter: RepositoryAdapter

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
        _binding = FragmentGithubUserProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        setupObservers()
        viewModel.start(args.login)
    }

    override fun onDestroyView() {
        binding.topRepositoriesList.clearItemDecorations()
        binding.starredRepositoriesList.clearItemDecorations()
        _binding = null
        super.onDestroyView()
    }

    private fun setupViews() {
        topRepositoriesAdapter = RepositoryAdapter()
        starredRepositoriesAdapter = RepositoryAdapter()

        with(binding) {
            topRepositoriesList.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_big),
                    LinearLayoutManager.HORIZONTAL
                )
            )
            starredRepositoriesList.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_big),
                    LinearLayoutManager.HORIZONTAL
                )
            )

            val topRepositoriesLayoutManager = object : LinearLayoutManager(requireContext()) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = (width * 75) / 100
                    return true
                }
            }
            topRepositoriesLayoutManager.orientation = RecyclerView.HORIZONTAL
            topRepositoriesList.layoutManager = topRepositoriesLayoutManager

            val starredRepositoriesLayoutManager = object : LinearLayoutManager(requireContext()) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = (width * 75) / 100
                    return true
                }
            }
            starredRepositoriesLayoutManager.orientation = RecyclerView.HORIZONTAL
            starredRepositoriesList.layoutManager = starredRepositoriesLayoutManager

            topRepositoriesList.adapter = topRepositoriesAdapter
            starredRepositoriesList.adapter = starredRepositoriesAdapter
        }
    }

    private fun setupListeners() {
        binding.content.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> showErrorDialog(uiState.error)
                is UiState.Loaded -> showContent(uiState.data)
                is UiState.Loading -> showLoading()
            }
        }
    }

    private fun showContent(userProfile: GitHubUserProfileUiModel) {
        with(binding) {
            content.isRefreshing = false

            Glide
                .with(requireContext())
                .load(userProfile.avatarUrl)
                .circleCrop()
                .into(binding.userProfileAvatar)

            userProfileName.text = userProfile.name
            userProfileLogin.text = userProfile.login

            if (userProfile.email.isNullOrEmpty()) {
                userProfileEmail.visible(false)
            } else {
                userProfileEmail.text = userProfile.email
                userProfileEmail.visible(true)
            }

            userProfileFollowersCounter.text = userProfile.followersTotalCount
            userProfileFollowingCounter.text = userProfile.followingTotalCount

            topRepositoriesAdapter.submitList(userProfile.topRepositories)
            starredRepositoriesAdapter.submitList(userProfile.starredRepositories)

            loading.visible(false)
            content.visible(true)
        }
    }

    private fun showLoading() {
        with(binding) {
            loading.visible(true)
            content.visible(false)
        }
    }

    private fun showErrorDialog(error: Throwable?) {
        binding.content.isRefreshing = false

        AlertDialog.Builder(context)
            .setMessage(UiErrorsMapper(resources).map(error))
            .create()
            .show()
    }

}