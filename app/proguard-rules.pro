# HealthSync ProGuard rules

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
}
-keepclasseswithmembers @kotlinx.serialization.Serializable class ** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.healthsync.app.**$$serializer { *; }
-keepclassmembers class com.healthsync.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.healthsync.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}
