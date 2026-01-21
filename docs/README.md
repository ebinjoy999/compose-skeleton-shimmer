# Screenshots

This folder contains GIF/image placeholders for documentation.

## Required Files:
- `feed_skeleton.gif` - Feed screen loading animation
- `profile_skeleton.gif` - Profile screen loading animation  
- `dashboard_skeleton.gif` - Dashboard screen loading animation

## How to Create Screenshots:

1. Run the sample app on an emulator
2. Use Android Studio's screen recorder or a tool like `scrcpy`
3. Record each screen during loading state
4. Convert to GIF using tools like `ffmpeg` or online converters
5. Optimize GIF size (recommended: < 1MB per file)

### Example ffmpeg command:
```bash
ffmpeg -i screen_recording.mp4 -vf "fps=15,scale=320:-1" -loop 0 feed_skeleton.gif
```
