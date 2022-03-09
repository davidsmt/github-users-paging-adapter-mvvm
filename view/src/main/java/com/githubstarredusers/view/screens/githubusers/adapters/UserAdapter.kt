package com.githubstarredusers.view.screens.githubusers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubstarredusers.presentation.models.GitHubUserUiModel
import com.githubstarredusers.view.R
import com.githubstarredusers.view.databinding.LayoutUserItemBinding


class UserAdapter(
    private val onItemClicked: (login: String) -> Unit
) : PagingDataAdapter<GitHubUserUiModel, UserAdapter.GitHubRepositoryHolder>(
    GitHubRepositoryItemCallback()
) {

    override fun onBindViewHolder(holder: GitHubRepositoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepositoryHolder =
        GitHubRepositoryHolder(
            binding = LayoutUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked
        )

    class GitHubRepositoryHolder(
        private val binding: LayoutUserItemBinding,
        private val onItemClicked: (login: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: GitHubUserUiModel?) {
            with(binding) {
                when (uiModel) {
                    null -> {
                        userProfileLogin.text = itemView.resources.getString(R.string.loading)
                    }
                    else -> {
                        Glide
                            .with(binding.root.context)
                            .load(uiModel.avatarUrl)
                            .circleCrop()
                            .into(binding.userProfileAvatar)

                        userProfileLogin.text = uiModel.login
                        userDescription.text = uiModel.url

                        root.setOnClickListener {
                            onItemClicked(uiModel.login)
                        }
                    }
                }
            }
        }
    }

    class GitHubRepositoryItemCallback : DiffUtil.ItemCallback<GitHubUserUiModel>() {
        override fun areItemsTheSame(
            old: GitHubUserUiModel,
            new: GitHubUserUiModel
        ): Boolean = old.id == new.id

        override fun areContentsTheSame(
            old: GitHubUserUiModel,
            new: GitHubUserUiModel
        ): Boolean = old == new
    }
}