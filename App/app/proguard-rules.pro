# AntiChaos ProGuard Rules

# Keep Room entities
-keep class com.antichaos.app.data.local.entity.** { *; }

# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class hilt_aggregated_deps.** { *; }

# Keep JSON serialization
-keepattributes Signature
-keepattributes InnerClasses
