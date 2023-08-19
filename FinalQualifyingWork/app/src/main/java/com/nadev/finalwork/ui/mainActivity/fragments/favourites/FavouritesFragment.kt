package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nadev.finalwork.R
import com.nadev.finalwork.data.models.saved.comments.SavedComment
import com.nadev.finalwork.data.models.saved.links.SavedLink
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class FavouritesFragment : Fragment() {
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouritesViewModel.loadPosts()
        return ComposeView(requireContext()).apply {
            setContent {
                val mCheckedState = remember { mutableStateOf(false) }
                Scaffold(
                    content = {
                        Surface(
                            modifier = Modifier.padding(top = it.calculateTopPadding())
                                .fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row {
                                    Text(text = "posts")
                                    Switch(checked = mCheckedState.value, onCheckedChange = {
                                        mCheckedState.value = it
                                        if (it) {
                                            favouritesViewModel.loadComments()
                                        } else favouritesViewModel.loadPosts()
                                    })
                                    Text(text = "comments")
                                }
                                if (!mCheckedState.value) {
                                    Log.d("MyLog", "saved posts")
                                    PostsList(posts = favouritesViewModel.pagedPosts)
                                } else {
                                    CommentsList(comments = favouritesViewModel.pagedComments)
                                }

                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun PostsList(posts: StateFlow<Flow<PagingData<SavedLink>>?>) {
        val postsState = posts.collectAsState().value
        postsState?.let { state ->
            val lazyPostItems: LazyPagingItems<SavedLink> = state.collectAsLazyPagingItems()
            LazyColumn {
                items(lazyPostItems.itemCount) { index ->
                    val item = lazyPostItems[index] ?: return@items
                    PostScreen(post = item)
                }
            }
        }
    }

    @Composable
    fun PostScreen(post: SavedLink) {
        val textSizeCommon = 20.sp
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier) {
                    post.linkData?.title?.let { title ->
                        Text(
                            text = title,
                            fontSize = textSizeCommon,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_person_add),
                            "",
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }
                }
                post.linkData?.url?.let { imgUrl ->
                    GlideImage(imageModel = { imgUrl }, Modifier.requiredHeightIn(0.dp, 200.dp))
                }
                Row {
                    post.linkData?.author?.let { author ->
                        Text(
                            text = author,
                            fontSize = textSizeCommon,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    post.linkData?.numComments?.let { numComments ->
                        Text(text = numComments.toString(), fontSize = textSizeCommon)
                    }
                    Icon(
                        Icons.Default.Comment,
                        ""
                    )
                }
            }
        }
    }

    @Composable
    fun CommentsList(comments: StateFlow<Flow<PagingData<SavedComment>>?>) {
        val commentsState = comments.collectAsState().value
        commentsState?.let { state ->
            val lazyPostItems: LazyPagingItems<SavedComment> = state.collectAsLazyPagingItems()
            LazyColumn {
                items(lazyPostItems.itemCount) { index ->
                    val item = lazyPostItems[index] ?: return@items
                    CommentScreen(comment = item)
                }
            }
        }
    }

    @Composable
    fun CommentScreen(comment: SavedComment) {
        val textSizeCommon = 20.sp
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier) {
                    comment.commentData?.body?.let { body ->
                        Text(text = body, fontSize = textSizeCommon, modifier = Modifier.weight(1f))
                    }

                }
                Row {
                    comment.commentData?.author?.let { author ->
                        Text(
                            text = author,
                            fontSize = textSizeCommon,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }

}