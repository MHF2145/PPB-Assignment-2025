package com.example.forebuck

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.gridlayout.widget.GridLayout
import android.util.TypedValue
import android.graphics.Color // Import Color jika ingin menggunakan setBackgroundColor

class HomeFragment : Fragment() {

    private var isLoggedIn = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // === Root Layout (Vertical) ===
        val rootLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Optional: set a background color for the entire screen
            // setBackgroundColor(Color.WHITE)
        }

        // === HEADER ===
        val header = TextView(requireContext()).apply {
            text = "ForeBuck"
            textSize = 28f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 32.dpToPx(), 0, 24.dpToPx())
            }
        }
        rootLayout.addView(header)

        // === SCROLLABLE CONTENT AREA ===
        val scrollView = ScrollView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }

        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32.dpToPx())
            // gravity = Gravity.CENTER_HORIZONTAL // Uncomment if you want to center ALL content
        }

        // === Horizontal Menu Cards ===
        val horizontalScroll = HorizontalScrollView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16.dpToPx(), 0, 16.dpToPx())
            }
        }
        val cardRow = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        // Pastikan drawable ini ada di res/drawable/
        val menuItems = listOf(
            "Latte" to R.drawable.menu_latte,
            "Espresso" to R.drawable.menu_espresso,
            "Cappuccino" to R.drawable.menu_cappuccino,
            "Matcha" to R.drawable.menu_matcha,
            "Americano" to R.drawable.menu_americano
        )

        for ((name, imageRes) in menuItems) {
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16.dpToPx())
                setBackgroundColor(Color.WHITE) // Contoh: latar belakang putih
                gravity = Gravity.CENTER_HORIZONTAL

                val img = ImageView(context).apply {
                    setImageResource(imageRes)
                    layoutParams = LinearLayout.LayoutParams(200.dpToPx(), 200.dpToPx())
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }

                val title = TextView(context).apply {
                    text = name
                    textSize = 16f
                    gravity = Gravity.CENTER
                }

                addView(img)
                addView(title)
            }

            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16.dpToPx(), 0, 16.dpToPx(), 0)
            }
            card.layoutParams = params

            cardRow.addView(card)
        }

        horizontalScroll.addView(cardRow)
        contentLayout.addView(horizontalScroll)

        // === Pickup & Delivery Buttons (1 Row) ===
        val buttonRow = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            weightSum = 2f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16.dpToPx(), 0, 16.dpToPx())
            }
        }

        val pickupBtn = Button(requireContext()).apply {
            text = "Pickup"
            val params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(8.dpToPx(), 0, 8.dpToPx(), 0)
            }
            layoutParams = params
            setOnClickListener { goToNext("pickup") }
        }

        val deliveryBtn = Button(requireContext()).apply {
            text = "Delivery"
            val params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(8.dpToPx(), 0, 8.dpToPx(), 0)
            }
            layoutParams = params
            setOnClickListener { goToNext("delivery") }
        }

        buttonRow.addView(pickupBtn)
        buttonRow.addView(deliveryBtn)
        contentLayout.addView(buttonRow)

        // === Best Seller Section ===
        val bestSellerTitle = TextView(context).apply {
            text = "Menu Terlaris"
            textSize = 20f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 24.dpToPx(), 0, 8.dpToPx())
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val bestSellerImage = ImageView(context).apply {
            setImageResource(R.drawable.menu_best_seller) // Pastikan drawable ini ada di res/drawable/
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                500.dpToPx()
            )
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        contentLayout.addView(bestSellerTitle)
        contentLayout.addView(bestSellerImage)

        // === Grid Menu (2 Columns) ===
        val gridLayout = GridLayout(requireContext()).apply {
            columnCount = 2
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                // Mengurangi margin horizontal untuk memberikan lebih banyak ruang pada card
                setMargins(4.dpToPx(), 24.dpToPx(), 4.dpToPx(), 24.dpToPx()) // Margin kiri/kanan dikurangi drastis
            }
        }

        // Daftar menu dengan ID gambar yang sesuai
        val gridMenuWithImages = listOf(
            "Hazelnut" to R.drawable.menu_hazelnut,
            "Caramel" to R.drawable.menu_caramel,
            "Mocha" to R.drawable.menu_mocha,
            "Flat White" to R.drawable.menu_flat_white,
            "Macchiato" to R.drawable.menu_macchiato,
            "Vanilla Latte" to R.drawable.menu_vanilla_latte,
            "Affogato" to R.drawable.menu_affogato,
            "Ristretto" to R.drawable.menu_ristretto,
            "Cold Brew" to R.drawable.menu_cold_brew,
            "Irish Coffee" to R.drawable.menu_irish_coffee
        )

        for ((name, imageRes) in gridMenuWithImages) {
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(4.dpToPx()) // Padding internal card dikurangi lebih jauh
                setBackgroundColor(Color.WHITE) // Contoh: latar belakang putih
                gravity = Gravity.CENTER_HORIZONTAL

                val img = ImageView(context).apply {
                    setImageResource(imageRes) // Menggunakan gambar spesifik dari daftar
                    // Meningkatkan ukuran gambar agar terlihat lebih besar di card yang lebih lebar
                    layoutParams = LinearLayout.LayoutParams(380.dpToPx(), 380.dpToPx()).apply { // Ukuran gambar diperbesar lagi
                        bottomMargin = 4.dpToPx() // Margin bawah gambar juga dikurangi
                    }
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }

                val label = TextView(context).apply {
                    text = name
                    gravity = Gravity.CENTER
                    textSize = 16f
                }

                addView(img)
                addView(label)
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            // Mengurangi margin antar card di dalam grid lebih jauh
            (params as ViewGroup.MarginLayoutParams).setMargins(4.dpToPx(), 4.dpToPx(), 4.dpToPx(), 4.dpToPx()) // Margin antar card dikurangi

            card.layoutParams = params
            gridLayout.addView(card)
        }

        contentLayout.addView(gridLayout)
        scrollView.addView(contentLayout)

        // === FOOTER Navigasi ===
        val footerNav = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            weightSum = 2f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16.dpToPx())
            gravity = Gravity.CENTER
            // setBackgroundColor(Color.parseColor("#E0E0E0"))
        }

        val homeBtn = Button(requireContext()).apply {
            text = "Beranda"
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(8.dpToPx(), 0, 8.dpToPx(), 0)
            }
            setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }

        val orderBtn = Button(requireContext()).apply {
            text = "Order"
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(8.dpToPx(), 0, 8.dpToPx(), 0)
            }
            setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, OrderFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        footerNav.addView(homeBtn)
        footerNav.addView(orderBtn)

        // === Final Assembly ===
        rootLayout.addView(scrollView)
        rootLayout.addView(footerNav)

        return rootLayout
    }

    private fun goToNext(type: String) {
        if (isLoggedIn) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderFragment())
                .addToBackStack(null)
                .commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment(type) {
                    isLoggedIn = true
                })
                .addToBackStack(null)
                .commit()
        }
    }

    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}