// 主工程框架
include(":app")

// 依赖的基础类库
include(":lib_base")
include(":lib_voice")
include(":lib_network")

// 功能模块
include(":module_app_manager")
include(":module_constellation")
include(":module_developer")
include(":module_joke")
include(":module_map")
include(":module_setting")
include(":module_voice_setting")
include(":module_weather")
include(":module_chat")

rootProject.name = "AiVoiceApp"
rootProject.buildFileName = "build.gradle.kts"


