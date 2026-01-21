package com.ebin.skeleton.sample.data

/**
 * Sample data models for the demo app.
 */

data class FeedPost(
    val id: Int,
    val title: String,
    val description: String,
    val author: String,
    val timeAgo: String,
    val likes: Int,
    val comments: Int
)

data class UserProfile(
    val name: String,
    val handle: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val posts: Int
)

data class DashboardTile(
    val id: Int,
    val title: String,
    val value: String,
    val subtitle: String
)

/**
 * Sample data provider for demo purposes.
 */
object SampleData {
    
    val feedPosts = listOf(
        FeedPost(
            id = 1,
            title = "Getting Started with Jetpack Compose",
            description = "Learn how to build modern Android UIs with declarative components and state management.",
            author = "Android Team",
            timeAgo = "2h ago",
            likes = 245,
            comments = 32
        ),
        FeedPost(
            id = 2,
            title = "Material 3 Design Guidelines",
            description = "Explore the new Material You design system and how to implement dynamic colors.",
            author = "Design Studio",
            timeAgo = "4h ago",
            likes = 189,
            comments = 28
        ),
        FeedPost(
            id = 3,
            title = "Skeleton Loading Best Practices",
            description = "Why skeleton screens improve perceived performance and user experience.",
            author = "UX Weekly",
            timeAgo = "6h ago",
            likes = 312,
            comments = 45
        ),
        FeedPost(
            id = 4,
            title = "Kotlin Coroutines Deep Dive",
            description = "Understanding structured concurrency and flow in modern Android development.",
            author = "Kotlin Blog",
            timeAgo = "8h ago",
            likes = 421,
            comments = 67
        ),
        FeedPost(
            id = 5,
            title = "Building Accessible Android Apps",
            description = "Tips and techniques for creating inclusive mobile experiences for everyone.",
            author = "A11y Guide",
            timeAgo = "12h ago",
            likes = 156,
            comments = 19
        )
    )
    
    val userProfile = UserProfile(
        name = "Alex Developer",
        handle = "@alexdev",
        bio = "Android developer passionate about Jetpack Compose and modern UI patterns. Building beautiful apps one composable at a time.",
        followers = 1247,
        following = 328,
        posts = 89
    )
    
    val dashboardTiles = listOf(
        DashboardTile(
            id = 1,
            title = "Total Users",
            value = "12,456",
            subtitle = "+12% this week"
        ),
        DashboardTile(
            id = 2,
            title = "Revenue",
            value = "$8,234",
            subtitle = "+8% this month"
        ),
        DashboardTile(
            id = 3,
            title = "Active Sessions",
            value = "3,421",
            subtitle = "Currently online"
        ),
        DashboardTile(
            id = 4,
            title = "Downloads",
            value = "45,678",
            subtitle = "All time"
        ),
        DashboardTile(
            id = 5,
            title = "Avg. Rating",
            value = "4.8",
            subtitle = "Based on 2,341 reviews"
        ),
        DashboardTile(
            id = 6,
            title = "Response Time",
            value = "124ms",
            subtitle = "Server latency"
        )
    )
}
