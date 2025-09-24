# Pop-Art Effects with OpenGL Shaders (GLSL)

This project is a real-time image processing framework developed for a Bachelor's thesis, focusing on applying artistic (Pop Art-inspired) and physics-based post-processing effects using **OpenGL Shading Language (GLSL)**. The application provides a modular, extensible architecture and an intuitive Graphical User Interface (GUI) for interactive control and visual experimentation.

---

## 🌟 Features

* **20+ Real-Time Effects:** A comprehensive library of post-processing effects, categorized into:
    * **Pop Art Effects:** E.g., Posterization, Halftone, Tritone.
    * **Physical Effects:** E.g., Distortion, Water Ripple, Chromatic Aberration, Bloom.
* **Modular Pipeline:** Effects can be dynamically selected, combined, and re-ordered in the rendering pipeline for complex image manipulations.
* **Interactive Control (GUI):** A user-friendly interface built with **Java Swing** allows for multi-selection of effects and immediate visual feedback.
* **Fine-Grained Parameter Control:** Specialized dialogs (using sliders) allow users to adjust individual shader uniforms in real-time (e.g., controlling the strength of a Noise or Distortion effect).
* **Image I/O:** Functionality to load source textures and save the final rendered output.

---

## 🛠️ Architecture

The framework is designed for flexibility and stability, adhering to established software engineering principles.

* **Model-View-Controller (MVC) Pattern:** Ensures a clean separation between the data (Model), the user interface (View), and the application logic (Controller), promoting maintainability.
* **Rendering Core:** Utilizes **JOGL (Java OpenGL)** for high-performance, GPU-accelerated rendering.
* **Shader/Pipeline Management:** Dedicated classes (`ShaderManager`, `ShaderPipeline`, `PipelineManager`) handle the compilation, linking, and chaining of GLSL shaders, including the management of **Framebuffer Objects (FBOs)** for multi-pass rendering.
* **Quality Assurance:** Comprehensive **JUnit Integration Tests** verify the correct functionality of core components (e.g., `ShaderManagerTest`, `PipelineManagerTest`, `FramebufferObjectTest`).

---

## 🚀 Getting Started

### Prerequisites

To run the project, you need:

* **Java Development Kit (JDK)** (Version 11 or higher is recommended).
* A modern graphics card with **OpenGL 2.0** support or newer.
* The **JOGL (Java OpenGL) libraries** must be downloaded and accessible locally.

### Setup and Running (Manual JOGL Compilation)

Since this project uses specific JOGL dependencies, manual compilation and execution via the command line are required. **Please replace all bracketed placeholders (e.g., `[JOGL_DIR]`, `[PROJECT_ROOT]`) with your actual paths before execution.**

1.  **Clone the repository:**
    ```bash
    git clone [repository-url]
    cd [project-folder]
    ```

2.  **Define Environment Paths:**
    For clarity, define the path to your JOGL installation and your project's source root.

    ```bash
    # Set the path to your JOGL installation folder (containing the /jar subfolder)
    set JOGL_DIR=[C:\path\to\jogamp-all-platforms]
    # Set the path to your project's main source folder
    set SRC_DIR=[C:\path\to\glsl-popart\src\main\java]
    
    # Navigate to the JOGL Directory
    cd %JOGL_DIR%
    ```

3.  **Compile the Java Source Files:**
    Use `javac` to compile all source files, explicitly including the JOGL JAR files in the classpath (`-classpath`).

    ```bash
    javac -classpath "jar/gluegen-rt.jar;jar/jogl-all.jar" %SRC_DIR%\com\glsl\popart\Main.java %SRC_DIR%\com\glsl\popart\utils\*.java %SRC_DIR%\com\glsl\popart\model\*.java %SRC_DIR%\com\glsl\popart\controller\*.java
    ```

4.  **Run the Application:**
    Execute the compiled application (`com.glsl.popart.Main`). Ensure both the JOGL JARs and the root source directory (`%SRC_DIR%`) are correctly specified in the classpath.

    ```bash
    java -classpath "jar\gluegen-rt.jar;jar\jogl-all.jar;%SRC_DIR%" com.glsl.popart.Main
    ```

---

## 💻 Technologies Used

| Technology | Purpose |
| :--- | :--- |
| **Java** | Core programming language. |
| **JOGL** | Java Bindings for OpenGL, used for GPU rendering. |
| **GLSL** | OpenGL Shading Language, used to write all visual effects. |
| **Java Swing** | Used for building the Graphical User Interface (GUI). |
| **JUnit** | Integration testing framework. |
| **FBO** | Framebuffer Objects, essential for multi-pass effects. |

