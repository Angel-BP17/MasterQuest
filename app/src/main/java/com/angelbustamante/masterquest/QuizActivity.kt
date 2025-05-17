package com.angelbustamante.masterquest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.angelbustamante.masterquest.databinding.ActivityQuizBinding
import flashcard.Question
import flashcard.ui.QuestionFragment

class QuizActivity : AppCompatActivity() {

    private lateinit var ui: ActivityQuizBinding
    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val level = intent.getStringExtra("level") ?: "easy"
        questions = when (level) {
            "easy"   -> easyQuestions
            "medium" -> intermediateQuestions
            else     -> advancedQuestions
        }

        showQuestion()
    }


    private fun showQuestion() {
        if (currentQuestionIndex >= questions.size) {
            showResult()
            return
        }

        // actualiza texto “Pregunta X / N”
        ui.progressText.text = "Pregunta ${currentQuestionIndex + 1}/${questions.size}"

        // instancia el fragmento con callback
        val q = questions[currentQuestionIndex]
        val frag = QuestionFragment(q) { selected ->
            checkAnswer(selected)
        }

        // reemplaza fragmento
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frag)
            .commit()
    }


    private fun checkAnswer(selectedOption: Int) {
        val q = questions[currentQuestionIndex]

        val frag =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as? QuestionFragment
                ?: return

        frag.highlightCorrectAnswer(
            q.correctAnswerIndex,
            selectedOption,
        )

        if (selectedOption == q.correctAnswerIndex) score++

        currentQuestionIndex++

        Handler(Looper.getMainLooper()).postDelayed({
            frag.resetButtons()
            showQuestion()
        }, 1500)
    }

    private fun showResult() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("score", score)
            putExtra("total", questions.size)
        }
        startActivity(intent)
        finish()
    }

    private val easyQuestions = listOf<Question>(
        Question(
            "¿Qué lenguaje se usa principalmente para desarrollar aplicaciones Android?",
            listOf("Kotlin", "Python", "Swift", "JavaScript"),
            0
        ),
        Question(
            "¿Cuál es el archivo principal donde defines la interfaz de usuario en Android?",
            listOf("activity_main.xml", "MainActivity.kt", "AndroidManifest.xml", "styles.xml"),
            0
        ),
        Question(
            "¿Cuál es la función que se ejecuta al crear una Activity?",
            listOf("onCreate", "onStart", "onResume", "onDestroy"),
            0
        ),
        Question(
            "¿Qué componente define la estructura visual de una pantalla?",
            listOf("Activity", "Fragment", "Service", "Broadcast Receiver"),
            0
        ),
        Question(
            "¿Cuál es la extensión de los archivos de código en Kotlin?",
            listOf(".java", ".kt", ".kotlin", ".xml"),
            1
        ),
        Question(
            "¿Qué elemento se usa para mostrar texto en la pantalla?",
            listOf("Button", "TextView", "EditText", "ImageView"),
            1
        ),
        Question(
            "¿Cuál es el archivo donde configuras permisos y componentes de la app?",
            listOf("build.gradle", "AndroidManifest.xml", "gradle.properties", "proguard-rules.pro"),
            1
        ),
        Question(
            "¿Cómo se llama el archivo que controla las dependencias del proyecto?",
            listOf("AndroidManifest.xml", "build.gradle", "settings.gradle", "gradle-wrapper.properties"),
            1
        ),
        Question(
            "¿Qué método se usa para asignar el layout a una Activity?",
            listOf("setContentView", "inflate", "findViewById", "onCreate"),
            0
        ),
        Question(
            "¿Cuál es el IDE más usado para desarrollar en Android Kotlin?",
            listOf("Visual Studio Code", "IntelliJ IDEA", "Android Studio", "Eclipse"),
            2
        ),
        Question(
            "¿Qué se usa para referenciar un elemento del layout en Kotlin?",
            listOf("findViewById", "getElement", "selectView", "setView"),
            0
        ),
        Question(
            "¿Cómo se declara una variable inmutable en Kotlin?",
            listOf("var", "val", "const", "let"),
            1
        ),
        Question(
            "¿Cuál es el nombre del archivo donde se escriben estilos de la app?",
            listOf("colors.xml", "styles.xml", "themes.xml", "layouts.xml"),
            1
        ),
        Question(
            "¿Qué componente permite navegar entre pantallas en Android?",
            listOf("Intent", "Service", "Broadcast Receiver", "Content Provider"),
            0
        ),
        Question(
            "¿Qué palabra clave se usa para crear una función en Kotlin?",
            listOf("function", "fun", "def", "func"),
            1
        ),
        Question(
            "¿Qué componente se usa para ejecutar tareas en segundo plano?",
            listOf("Service", "Activity", "Fragment", "Broadcast Receiver"),
            0
        ),
        Question(
            "¿Qué clase se usa para mostrar mensajes cortos en pantalla?",
            listOf("Snackbar", "Dialog", "Toast", "Alert"),
            2
        ),
        Question(
            "¿Cuál es la forma correcta de iniciar una nueva Activity?",
            listOf("startActivity(Intent)", "launchActivity()", "openActivity()", "new Activity()"),
            0
        ),
        Question(
            "¿Qué es un Fragment en Android?",
            listOf("Una pantalla independiente", "Un componente para interfaces modulares", "Una base de datos", "Una librería externa"),
            1
        ),
        Question(
            "¿Cuál es el ciclo de vida inicial de una Activity?",
            listOf("onDestroy → onStop → onPause", "onCreate → onStart → onResume", "onResume → onPause → onStop", "onStart → onResume → onCreate"),
            1
        )
    )

    private val intermediateQuestions = listOf<Question>(
        Question(
            "¿Cuál es la diferencia entre LiveData y MutableLiveData?",
            listOf(
                "LiveData vive fuera del ciclo de vida; MutableLiveData no",
                "LiveData es de solo lectura; MutableLiveData permite postear/ setear valores", // ✔
                "No existe diferencia, son aliases",
                "LiveData solo funciona en Fragments; MutableLiveData en Activities"
            ),
            1
        ),
        Question(
            "¿Para qué sirve la clase ViewModel en Android Architecture Components?",
            listOf(
                "Crear hilos de red directamente",
                "Acceder a sensores del dispositivo",
                "Mantener datos de UI durante cambios de configuración",                          // ✔
                "Renderizar la vista"
            ),
            2
        ),
        Question(
            "En el ciclo de vida de una Activity, ¿qué método se llama inmediatamente DESPUÉS de onPause()?",
            listOf(
                "onDestroy()",
                "onStop()",                                                                       // ✔
                "onStart()",
                "onRestart()"
            ),
            1
        ),
        Question(
            "RecyclerView: ¿cuál es la finalidad principal de onCreateViewHolder() en el Adapter?",
            listOf(
                "Notificar cambios en la lista",
                "Calcular la posición de scroll",
                "Inflar el layout del ítem y crear el ViewHolder",                                // ✔
                "Vincular datos al ViewHolder"
            ),
            2
        ),
        Question(
            "¿Qué alcance de corrutinas se suele usar dentro de un ViewModel?",
            listOf(
                "lifecycleScope",
                "viewModelScope",                                                                 // ✔
                "GlobalScope",
                "MainScope"
            ),
            1
        ),
        Question(
            "Para usar Navigation Component debes agregar en Gradle la dependencia con artefacto:",
            listOf(
                "navigation-fragment-ktx",                                                       // ✔
                "navigation-ui-compose",
                "lifecycle-runtime-ktx",
                "appcompat"
            ),
            0
        ),
        Question(
            "Si tu app necesita usar la cámara, ¿qué permiso declaras en AndroidManifest.xml?",
            listOf(
                "android.permission.MEDIA",
                "android.permission.HARDWARE_CAMERA",
                "android.permission.CAMERA",                                                     // ✔
                "android.permission.USE_CAMERA"
            ),
            2
        ),
        Question(
            "En Room, ¿qué anotación marca la clave primaria de una Entity?",
            listOf(
                "@Key",
                "@EntityId",
                "@Id",
                "@PrimaryKey"                                                                    // ✔
            ),
            3
        ),
        Question(
            "View Binding se diferencia de findViewById porque…",
            listOf(
                "Genera clases seguras en tiempo de compilación para cada layout",               // ✔
                "Funciona solamente con Fragments",
                "Requiere reflexión en tiempo de ejecución",
                "No necesita habilitarse en Gradle"
            ),
            0
        ),
        Question(
            "Dentro de un layout con Data Binding, ¿qué se usa para obtener una propiedad de ViewModel?",
            listOf(
                "#{viewModel.prop}",
                "@{viewModel.prop}",                                                             // ✔
                "\${viewModel.prop}",
                "&&viewModel.prop"
            ),
            1
        ),
        Question(
            "¿Cuál es una ventaja de las sealed classes en Kotlin al modelar estados de UI?",
            listOf(
                "Generan Room DAOs",
                "Se instancian más rápido que las data class",
                "Se serializan automáticamente a JSON",
                "El when es exhaustivo sin necesidad de else"                                    // ✔
            ),
            3
        ),
        Question(
            "¿Qué método utilizas para iniciar un Service que debe ejecutarse de forma indefinida?",
            listOf(
                "startService()",                                                               // ✔
                "bindService()",
                "startIntentService()",
                "startForeground()"
            ),
            0
        ),
        Question(
            "Para abrir una página web con un Intent implícito, ¿qué acción se utiliza?",
            listOf(
                "Intent.ACTION_WEB",
                "Intent.ACTION_BROWSE",
                "Intent.ACTION_VIEW",                                                           // ✔
                "Intent.ACTION_SEND"
            ),
            2
        ),
        Question(
            "WorkManager: al terminar tu tarea satisfactoriamente debes devolver…",
            listOf(
                "Result.retry()",
                "Result.complete()",
                "Result.failure()",
                "Result.success()"                                                              // ✔
            ),
            3
        ),
        Question(
            "Con Safe Args, la clase generada para navegar desde un Fragment se llama…",
            listOf(
                "NavCommand",
                "FragmentDirections",                                                           // ✔
                "ActionBuilder",
                "SafeNav"
            ),
            1
        ),
        Question(
            "En el ciclo de vida de un Fragment, ¿qué callback se ejecuta ANTES de onCreate()?",
            listOf(
                "onStart()",
                "onAttach()",                                                                   // ✔
                "onViewCreated()",
                "onResume()"
            ),
            1
        ),
        Question(
            "¿Cuál es la unidad recomendada para el tamaño de texto en Android?",
            listOf(
                "dp",
                "px",
                "sp",                                                                           // ✔
                "pt"
            ),
            2
        ),
        Question(
            "Para habilitar kapt (Kotlin Annotation Processing) en un módulo Gradle usas:",
            listOf(
                "id(\"kotlin-kapt\")",                                                         // ✔
                "apply plugin: \"kotlin-parcelize\"",
                "kapt.enable = true",
                "annotationProcessor()"
            ),
            0
        ),
        Question(
            "¿Cómo registras un BroadcastReceiver SOLO en tiempo de ejecución?",
            listOf(
                "Con registerReceiver() en código",                                             // ✔
                "Extendiendo Application",
                "No es posible",
                "Añadiendo <receiver> en AndroidManifest"
            ),
            0
        ),
        Question(
            "En ConstraintLayout, la opción de ChainStyle que centra todos los elementos con espacio equitativo es:",
            listOf(
                "spreadInside",
                "weighted",
                "packed",
                "spread"                                                                       // ✔
            ),
            3
        )
    )

    private val advancedQuestions = listOf<Question>(
        Question(
            "¿Cuál es la diferencia principal entre Dispatchers.IO y Dispatchers.Default en Kotlin Coroutines?",
            listOf(
                "IO se ejecuta en el Main thread; Default crea un hilo nuevo por coroutine",
                "IO es exclusivo de Android; Default es exclusivo de JVM",
                "IO usa un pool ilimitado de hilos orientado a operaciones de bloqueo; Default emplea CPU-bound threads", // ✔
                "IO garantiza orden FIFO; Default no"
            ),
            2
        ),
        Question(
            "En Jetpack Compose, ¿qué hace rememberSaveable que no haga remember?",
            listOf(
                "Evita recomposiciones del composable",
                "Sirve para observar Flows dentro del composable",
                "Serializa el árbol completo de UI",
                "Persiste el estado en casos de configuración y proceso muerto-revivido"                                         // ✔
            ),
            3
        ),
        Question(
            "¿Qué anotación se usa en Hilt para vincular una interface a su implementación en el grafo de dependencias?",
            listOf(
                "@Provides",
                "@Binds",          // ✔
                "@Inject",
                "@Singleton"
            ),
            1
        ),
        Question(
            "Room: ¿qué estrategia de migración se usa cuando quieres recrear por completo la BD perdiendo datos?",
            listOf(
                "addMigrations()",
                "autoMigrations()",
                "fallbackToDestructiveMigration()",   // ✔
                "inMemory()"
            ),
            2
        ),
        Question(
            "Paging 3: al implementar un RemoteMediator debes sobreescribir el método…",
            listOf(
                "initialize()",
                "load()",          // ✔
                "getRefreshKey()",
                "onFetch()"
            ),
            1
        ),
        Question(
            "¿Qué flag de ProGuard/R8 mantiene las anotaciones durante el minificado?",
            listOf(
                "-keep class **@interface",
                "-dontobfuscate",
                "-printmapping",
                "-keepattributes *Annotation*"        // ✔
            ),
            3
        ),
        Question(
            "WorkManager elige un Scheduler distinto según API. ¿Qué Scheduler usa en API 23+ cuando los servicios de Google Play NO están presentes?",
            listOf(
                "AlarmScheduler",
                "FirebaseJobScheduler",
                "JobSchedulerScheduler",              // ✔
                "GcmScheduler"
            ),
            2
        ),
        Question(
            "En Navigation Component, para compartir ViewModel entre dos destinations del mismo graph se usa:",
            listOf(
                "activityViewModels()",
                "navGraphViewModels()",               // ✔
                "hiltViewModel()",
                "sharedViewModel()"
            ),
            1
        ),
        Question(
            "¿Cuál es la clave para activar el modo incremental de kapt y mejorar tiempos de compilación?",
            listOf(
                "kapt.incremental.apt=true",
                "kapt.use.worker.api=true",
                "kapt.incremental=true",              // ✔
                "kapt.fastInit=true"
            ),
            2
        ),
        Question(
            "LiveData vs Flow: ¿qué característica es exclusiva de Flow?",
            listOf(
                "Respeto al ciclo de vida",
                "Operadores de transformación en cadena (map, flatMapConcat…)", // ✔
                "Hot stream por defecto",
                "Soporte en ViewModel"
            ),
            1
        ),
        Question(
            "¿Qué opción de Gradle permite compilar varios módulos en paralelo?",
            listOf(
                "org.gradle.configuration-cache=true",
                "kotlin.mpp.enableCInteropCommonization",
                "android.enableSeparateApkRes",
                "org.gradle.parallel=true"            // ✔
            ),
            3
        ),
        Question(
            "Para optimizar el arranque, Android App Startup recomienda inicializar componentes…",
            listOf(
                "en Application.onCreate siempre",
                "perezosos (lazy) y sólo cuando se necesitan",                  // ✔
                "mediante ContentProviders estáticos",
                "mediante reflection en tiempo de ejecución"
            ),
            1
        ),
        Question(
            "En Jetpack Compose, ¿qué modifier habilita la medición de recomposiciones en tiempo de ejecución?",
            listOf(
                "Modifier.debugInspectorInfo {}",
                "Modifier.composed {}",
                "Modifier.pointerInput {}",
                "No existe tal modifier"                                       // ✔
            ),
            3
        ),
        Question(
            "Al usar DataStore Preferences, ¿qué tipo devuelve dataStore.data?",
            listOf(
                "StateFlow<Preferences>",
                "PreferencesDataStore",
                "Flow<Preferences>",                                           // ✔
                "LiveData<Preferences>"
            ),
            2
        ),
        Question(
            "¿Cuál es el propósito de la anotación @Parcelize en Kotlin Android Extensions?",
            listOf(
                "Serializar JSON automáticamente",
                "Generar Parcelable implementado por el compilador",           // ✔
                "Inyectar dependencias",
                "Convertir a Room Entity"
            ),
            1
        ),
        Question(
            "Cuando usas StrictMode para depurar, ¿qué política detecta operaciones de red en el hilo principal?",
            listOf(
                "VmPolicy detectAll()",
                "VmPolicy penaltyDeath()",
                "ThreadPolicy detectNetwork()",                                // ✔
                "ThreadPolicy detectDiskReads()"
            ),
            2
        ),
        Question(
            "Con CoroutineScope + lifecycleScope, ¿en qué momento se cancelan las coroutines lanzadas en onStart y ligadas a la actividad?",
            listOf(
                "En onPause",
                "Cuando la activity se destruye",
                "Nunca se cancelan automáticamente",
                "En onStop"                                                    // ✔
            ),
            3
        ),
        Question(
            "¿Qué ventaja aporta `suspend fun` + Retrofit + OkHttp sobre los Callbacks tradicionales?",
            listOf(
                "Evita completamente los hilos de fondo",
                "Puede ejecutarse en el hilo principal sin ANR gracias a suspensión", // ✔
                "Genera código nativo con NDK",
                "Usa reflection para parseo más rápido"
            ),
            1
        ),
        Question(
            "Al configurar un proyecto multiplataforma con Kotlin MPP, ¿qué target se usa para producir una librería consumible desde código Swift?",
            listOf(
                "darwin()",
                "appleFramework()",
                "ios()",                                                      // ✔
                "cocoapods()"
            ),
            2
        ),
        Question(
            "En Compose, `derivedStateOf { … }` se utiliza principalmente para…",
            listOf(
                "emitir side-effects dentro de UI",
                "obtener ViewModels dentro de composables",
                "crear keys de recomposición manual",
                "memorizar un valor derivado y recalcularlo sólo cuando cambia su input" // ✔
            ),
            3
        )
    )

}