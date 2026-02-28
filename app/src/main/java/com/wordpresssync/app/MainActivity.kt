package com.wordpresssync.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wordpresssync.app.api.model.WpPost
import com.wordpresssync.app.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: com.wordpresssync.app.data.SettingsRepository
    private lateinit var repository: com.wordpresssync.app.data.WordPressRepository
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = com.wordpresssync.app.data.SettingsRepository(this)
        repository = com.wordpresssync.app.data.WordPressRepository(settings)

        binding.editSiteUrl.setText(settings.siteBaseUrl ?: "")

        adapter = PostsAdapter(emptyList())
        binding.recyclerPosts.layoutManager = LinearLayoutManager(this)
        binding.recyclerPosts.adapter = adapter

        binding.btnSaveUrl.setOnClickListener { saveUrl() }
        binding.btnLoadPosts.setOnClickListener { loadPosts() }
    }

    private fun saveUrl() {
        val url = binding.editSiteUrl.text?.toString()?.trim()
        if (url.isNullOrBlank()) {
            Toast.makeText(this, "Введите URL сайта", Toast.LENGTH_SHORT).show()
            return
        }
        val normalized = if (url.startsWith("http")) url else "https://$url"
        settings.siteBaseUrl = normalized
        repository.clearApiCache()
        Toast.makeText(this, "URL сохранён", Toast.LENGTH_SHORT).show()
    }

    private fun loadPosts() {
        if (settings.siteBaseUrl.isNullOrBlank()) {
            binding.textError.visibility = View.VISIBLE
            binding.textError.text = getString(R.string.error_no_url)
            binding.recyclerPosts.visibility = View.GONE
            binding.textEmpty.visibility = View.GONE
            return
        }
        binding.textError.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerPosts.visibility = View.GONE
        binding.textEmpty.visibility = View.GONE

        lifecycleScope.launch {
            val result = repository.getPosts(perPage = 20, page = 1)
            binding.progressBar.visibility = View.GONE

            result.fold(
                onSuccess = { posts ->
                    if (posts.isEmpty()) {
                        binding.textEmpty.visibility = View.VISIBLE
                        binding.textEmpty.text = "Постов пока нет"
                    } else {
                        adapter.updatePosts(posts)
                        binding.recyclerPosts.visibility = View.VISIBLE
                    }
                },
                onFailure = { e ->
                    binding.textError.visibility = View.VISIBLE
                    binding.textError.text = "${getString(R.string.error_load)}: ${e.message}"
                }
            )
        }
    }
}
