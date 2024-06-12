plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val retrofitVersion = "2.9.0"

dependencies {
    implementation(project(":domain"))

    //retrofit
    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    api("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    api ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("javax.inject:javax.inject:1")


}
