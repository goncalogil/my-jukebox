import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("io.gitlab.arturbosch.detekt").version("1.21.0-RC2")
}

group = "com.goncalo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Test
	testImplementation("io.mockk:mockk:1.12.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	// Test Fixtures
	testImplementation("net.datafaker:datafaker:1.4.0")

	// Optimization
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// detekt configuration block.
detekt {
	// Version of Detekt that will be used. When unspecified the latest detekt
	// version found will be used. Override to stay on the same version.
	toolVersion = "1.21.0-RC2"

	// The directories where detekt looks for source files.
	// Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
	//source = files(
	//	"src/main/kotlin",
	//	"gensrc/main/kotlin"
	//)

	// Builds the AST in parallel. Rules are always executed in parallel.
	// Can lead to speedups in larger projects. `false` by default.
	parallel = false

	// Define the detekt configuration(s) you want to use.
	// Defaults to the default detekt configuration.
	//config = files("path/to/config.yml")

	// Applies the config files on top of detekt's default config file. `false` by default.
	buildUponDefaultConfig = false

	// Turns on all the rules. `false` by default.
	allRules = false

	// Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
	//baseline = file("src/")

	// Disables all default detekt rulesets and will only run detekt with custom rules
	// defined in plugins passed in with `detektPlugins` configuration. `false` by default.
	disableDefaultRuleSets = false

	// Adds debug output during task execution. `false` by default.
	debug = false

	// If set to `true` the build does not fail when the
	// maxIssues count was reached. Defaults to `false`.
	ignoreFailures = false

	// Specify the base path for file paths in the formatted reports.
	// If not set, all file paths reported will be absolute file path.
	basePath = projectDir.toString()
}
