package com.example.forebuck

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.gridlayout.widget.GridLayout
import android.graphics.Typeface
import android.graphics.Color
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentManager

class OrderFragment : Fragment() {

    // Menggabungkan semua menu menjadi satu daftar lengkap
    private val allMenuItems = listOf(
        Triple("Latte", 22000, R.drawable.menu_latte),
        Triple("Espresso", 18000, R.drawable.menu_espresso),
        Triple("Cappuccino", 23000, R.drawable.menu_cappuccino),
        Triple("Matcha", 25000, R.drawable.menu_matcha),
        Triple("Americano", 20000, R.drawable.menu_americano),
        Triple("Hazelnut", 24000, R.drawable.menu_hazelnut),
        Triple("Caramel", 24000, R.drawable.menu_caramel),
        Triple("Mocha", 26000, R.drawable.menu_mocha),
        Triple("Flat White", 23000, R.drawable.menu_flat_white),
        Triple("Macchiato", 25000, R.drawable.menu_macchiato),
        Triple("Vanilla Latte", 24000, R.drawable.menu_vanilla_latte),
        Triple("Affogato", 28000, R.drawable.menu_affogato),
        Triple("Ristretto", 20000, R.drawable.menu_ristretto),
        Triple("Cold Brew", 27000, R.drawable.menu_cold_brew),
        Triple("Irish Coffee", 29000, R.drawable.menu_irish_coffee)
    )

    private val selectedItems = mutableSetOf<String>()
    private lateinit var totalText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Root layout
        val rootLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
        }

        // --- HEADER Baru dengan Tombol Kembali ---
        val headerLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE) // Latar belakang header putih
        }

        val backButton = ImageView(requireContext()).apply {
            // Menggunakan ikon panah kembali bawaan Android
            setImageResource(android.R.drawable.ic_menu_close_clear_cancel) // Panah silang, akan diubah ke panah kiri
            // Untuk panah ke kiri, Anda bisa menggunakan Drawable khusus atau
            // jika ingin yang ada di sistem: android.R.drawable.arrow_left (tidak selalu ada/jelas)
            // Atau Anda bisa menggunakan ic_action_navigation_arrow_back (jika Anda memiliki ikon Material Design)
            // Untuk tampilan kotak, kita akan mengatur background drawable
            val backButtonSize = 32.dpToPx()
            layoutParams = LinearLayout.LayoutParams(backButtonSize, backButtonSize).apply {
                marginEnd = 16.dpToPx() // Spasi antara tombol dan judul
            }
            setBackgroundColor(Color.TRANSPARENT) // Tidak perlu background kotak, karena kita pakai drawable ikon
            // Jika Anda ingin efek kotak, Anda bisa buat drawable XML di res/drawable/button_back_background.xml
            // setBackgroundResource(R.drawable.button_back_background)
            setOnClickListener {
                // Kembali ke HomeFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
        }
        headerLayout.addView(backButton)

        val headerTitle = TextView(requireContext()).apply {
            text = "Pilih Pesanan Anda" // Judul untuk OrderFragment
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        headerLayout.addView(headerTitle)

        rootLayout.addView(headerLayout)
        // --- Akhir HEADER Baru ---


        val paddingPx = 32.dpToPx()

        val scrollView = ScrollView(requireContext())
        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(paddingPx)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val gridLayout = GridLayout(requireContext()).apply {
            columnCount = 2
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(4.dpToPx(), 24.dpToPx(), 4.dpToPx(), 24.dpToPx())
            }
        }

        for ((menuName, price, imageRes) in allMenuItems) {
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(4.dpToPx())
                setBackgroundColor(Color.WHITE)
                gravity = Gravity.CENTER_HORIZONTAL
                isClickable = true
                isFocusable = true

                val image = ImageView(context).apply {
                    setImageResource(imageRes)
                    layoutParams = LinearLayout.LayoutParams(380.dpToPx(), 380.dpToPx()).apply {
                        bottomMargin = 4.dpToPx()
                    }
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }

                val nameView = TextView(context).apply {
                    text = menuName
                    textSize = 16f
                    setTypeface(null, Typeface.BOLD)
                    gravity = Gravity.CENTER
                }

                val priceView = TextView(context).apply {
                    text = "Rp ${price}"
                    textSize = 14f
                    gravity = Gravity.CENTER
                }

                addView(image)
                addView(nameView)
                addView(priceView)

                val params = GridLayout.LayoutParams().apply {
                    width = 0
                    height = LinearLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                (params as ViewGroup.MarginLayoutParams).setMargins(4.dpToPx(), 4.dpToPx(), 4.dpToPx(), 4.dpToPx())
                layoutParams = params

                setOnClickListener {
                    if (selectedItems.contains(menuName)) {
                        selectedItems.remove(menuName)
                        setBackgroundColor(Color.WHITE)
                    } else {
                        selectedItems.add(menuName)
                        setBackgroundColor(Color.parseColor("#D0F0C0")) // Light green
                    }
                    updateTotal()
                }
            }

            gridLayout.addView(card)
        }

        contentLayout.addView(gridLayout)
        scrollView.addView(contentLayout)
        rootLayout.addView(scrollView, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1f
        ))

        // Footer: total & checkout
        val footer = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            weightSum = 2f
            gravity = Gravity.CENTER_VERTICAL
            setPadding(32.dpToPx(), 24.dpToPx(), 32.dpToPx(), 24.dpToPx())
            setBackgroundColor(Color.LTGRAY)
        }

        totalText = TextView(requireContext()).apply {
            text = "Total item: 0"
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val checkoutBtn = Button(requireContext()).apply {
            text = "Checkout"
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            setOnClickListener {
                Toast.makeText(requireContext(), "Pesanan berhasil dibuat!", Toast.LENGTH_SHORT).show()

                view?.postDelayed({
                    parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }, 5000)
            }
        }

        footer.addView(totalText)
        footer.addView(checkoutBtn)
        rootLayout.addView(footer)

        return rootLayout
    }

    private fun updateTotal() {
        totalText.text = "Total item: ${selectedItems.size}"
    }

    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}