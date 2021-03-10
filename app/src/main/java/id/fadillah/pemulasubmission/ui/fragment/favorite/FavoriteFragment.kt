package id.fadillah.pemulasubmission.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.databinding.FragmentFavoriteBinding
import id.fadillah.pemulasubmission.ui.adapter.MangaAdapter
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var mangaAdapter: MangaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        mangaAdapter = MangaAdapter()
        with(binding?.rvFav) {
            this?.layoutManager = GridLayoutManager(context, 2)
            this?.setHasFixedSize(true)
            this?.adapter = mangaAdapter
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            showRecyclerView(false)
            viewModel.getAllFavoriteManga().observe(viewLifecycleOwner, { manga ->
                if (manga != null) {
                    val data = manga.map {
                        MangaEntity(it.title, it.endpoint, it.thumbnail, bookmarked =  it.bookmarked)
                    }
                    if (data.isNotEmpty()) {
                        showRecyclerView(true)
                        mangaAdapter.setData(data)
                        mangaAdapter.notifyDataSetChanged()
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        showRecyclerView(false)
        viewModel.getAllFavoriteManga().observe(viewLifecycleOwner, { manga ->
            if (manga != null) {
                val data = manga.map {
                    MangaEntity(it.title, it.endpoint, it.thumbnail, bookmarked =  it.bookmarked)
                }
                if (data.isNotEmpty()) {
                    showRecyclerView(true)
                    mangaAdapter.setData(data)
                    mangaAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun showRecyclerView(show: Boolean) {
        if (show) {
            binding?.layoutNothing?.visibility = View.GONE
            binding?.rvFav?.visibility = View.VISIBLE
            binding?.tvCollection?.visibility = View.VISIBLE
        } else {
            binding?.layoutNothing?.visibility = View.VISIBLE
            binding?.rvFav?.visibility = View.GONE
            binding?.tvCollection?.visibility = View.GONE
        }
    }
}