package flashcard.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.angelbustamante.masterquest.R
import flashcard.Question

class QuestionFragment(private val question: Question, private val onOptionSelected: (Int) -> Unit) : Fragment(R.layout.fragment_question) {

    private lateinit var questionText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionText = view.findViewById(R.id.questionText)
        option1 = view.findViewById(R.id.option1)
        option2 = view.findViewById(R.id.option2)
        option3 = view.findViewById(R.id.option3)
        option4 = view.findViewById(R.id.option4)

        // Asignamos los valores de la pregunta a las vistas
        questionText.text = question.questionText
        option1.text = question.options[0]
        option2.text = question.options[1]
        option3.text = question.options[2]
        option4.text = question.options[3]

        // Configuramos los botones para manejar las respuestas
        option1.setOnClickListener { onOptionSelected(0) }
        option2.setOnClickListener { onOptionSelected(1) }
        option3.setOnClickListener { onOptionSelected(2) }
        option4.setOnClickListener { onOptionSelected(3) }
    }

    fun highlightCorrectAnswer(correctOption: Int, selectedOption: Int) {
        // Colorear los botones según la respuesta seleccionada
        val options = listOf(option1, option2, option3, option4)

        // Resaltar la respuesta seleccionada
        options[selectedOption].setBackgroundColor(resources.getColor(R.color.red))  // Rojo si incorrecto

        // Resaltar la opción correcta en verde
        options[correctOption].setBackgroundColor(resources.getColor(R.color.green))

        // Deshabilitar los botones para no permitir más interacciones
        options.forEach { it.isEnabled = false }
    }

    fun resetButtons() {
        val options = listOf(option1, option2, option3, option4)
        options.forEach {
            it.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            it.isEnabled = true
        }
    }
}