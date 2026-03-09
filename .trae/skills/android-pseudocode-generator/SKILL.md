---
name: "android-pseudocode-generator"
description: "Generates Android project pseudocode from PRD and Figma designs. Invoke when user provides requirements document and Figma URL asking for Android code generation."
---

# Android Pseudocode Generator

This skill generates Android project pseudocode based on:
1. Product Requirements Document (PRD)
2. Figma design specifications

## When to Invoke

- User provides a PRD and asks for Android code
- User shares a Figma URL and wants Android implementation
- User asks to generate Android pseudocode from design specs
- User wants to convert UI designs to Android code structure

## Input Requirements

The user should provide:
1. **PRD/需求文档**: Text content or file path
2. **Figma URL**: Link to the design (optional but recommended)

## Output Format

The skill generates:
1. **Project Structure**: Package organization
2. **Activity/Fragment Classes**: UI controllers
3. **ViewModel Classes**: Business logic
4. **Repository/Service Classes**: Data layer
5. **Data Models**: POJO/Kotlin data classes
6. **XML Layouts**: UI layout files
7. **Resource Files**: Strings, colors, dimensions

## Pseudocode Style

- Use Kotlin as primary language
- Follow MVVM architecture pattern
- Include Jetpack Compose or XML layouts based on context
- Add TODO comments for implementation details
- Include type annotations and return types

## Example Output Structure

```
📁 com.example.app
├── 📁 ui
│   ├── 📁 screens
│   │   └── MainActivity.kt
│   ├── 📁 components
│   │   └── CustomButton.kt
│   └── 📁 theme
│       └── Color.kt
├── 📁 viewmodel
│   └── MainViewModel.kt
├── 📁 repository
│   └── UserRepository.kt
├── 📁 model
│   └── User.kt
└── 📁 di
    └── AppModule.kt
```

## Guidelines

1. **Analyze PRD First**: Extract features, user flows, and data requirements
2. **Parse Figma Design**: Identify screens, components, colors, typography
3. **Generate Layer by Layer**:
   - Data models first
   - Repository/Service layer
   - ViewModel with business logic
   - UI layer (Activities/Fragments/Composables)
   - Resources and styling
4. **Add Comments**: Explain complex logic and TODOs
5. **Follow Conventions**: Use Android best practices and naming conventions

## Pseudocode Template

### Activity/Fragment
```kotlin
class ScreenNameActivity : AppCompatActivity() {
    
    // ViewModel instance
    private lateinit var viewModel: ScreenNameViewModel
    
    // UI Components references
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_name)
        
        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ScreenNameViewModel::class.java)
        
        // Initialize views
        initViews()
        
        // Setup observers
        setupObservers()
        
        // Load data
        viewModel.loadData()
    }
    
    private fun initViews() {
        // TODO: Find views by ID
        // TODO: Setup RecyclerView adapter
        // TODO: Setup click listeners
    }
    
    private fun setupObservers() {
        // TODO: Observe LiveData/StateFlow from ViewModel
        // TODO: Update UI based on state changes
    }
}
```

### ViewModel
```kotlin
class ScreenNameViewModel(
    private val repository: DataRepository
) : ViewModel() {
    
    // State holders
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    // Data holders
    private val _data = MutableLiveData<List<DataModel>>()
    val data: LiveData<List<DataModel>> = _data
    
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = repository.fetchData()
                _data.value = result
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }
    
    fun onUserAction(action: UserAction) {
        // TODO: Handle user interactions
    }
}
```

### Repository
```kotlin
class DataRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource
) : DataRepository {
    
    override suspend fun fetchData(): List<DataModel> {
        // TODO: Check cache first
        // TODO: Fetch from API if needed
        // TODO: Save to cache
        return apiService.getData()
    }
    
    override suspend fun saveData(data: DataModel) {
        // TODO: Save to local database
    }
}
```

### Data Model
```kotlin
data class DataModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val createdAt: Long
)
```

### Jetpack Compose UI
```kotlin
@Composable
fun ScreenNameScreen(
    viewModel: ScreenNameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val data by viewModel.data.observeAsState(emptyList())
    
    Scaffold(
        topBar = { AppTopBar(title = "Screen Title") },
        content = { padding ->
            when (uiState) {
                is UiState.Loading -> LoadingIndicator()
                is UiState.Success -> DataList(
                    items = data,
                    modifier = Modifier.padding(padding)
                )
                is UiState.Error -> ErrorMessage(
                    message = (uiState as UiState.Error).message
                )
            }
        }
    )
}

@Composable
fun DataList(
    items: List<DataModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            DataListItem(item = item)
        }
    }
}

@Composable
fun DataListItem(item: DataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
```

### XML Layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Steps to Generate

1. **Parse Input**: Extract information from PRD and Figma
2. **Identify Screens**: List all screens and their purposes
3. **Define Data Models**: Create classes based on data requirements
4. **Design Repository**: Define data access patterns
5. **Create ViewModels**: Map business logic to UI states
6. **Build UI Layer**: Generate Activities/Fragments/Composables
7. **Add Resources**: Colors, strings, dimensions, styles
8. **Review & Refine**: Ensure consistency and completeness

## Notes

- Pseudocode should be detailed enough to guide implementation
- Include comments explaining "why" not just "what"
- Follow Android architecture best practices
- Consider edge cases and error handling
- Include navigation logic if multiple screens
