package com.example.task1.domain

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://wundkhpnhtfbzkpppynn.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind1bmRraHBuaHRmYnprcHBweW5uIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MzczMTMsImV4cCI6MjA0MTAxMzMxM30.FWjsqSqPMQf0fQHbhkgoaoGLWEzl_J9CfFQLWRTt6KM"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}