# Screenshots

## Required Files:
- `feed_skeleton.gif` - Feed screen loading animation
- `profile_skeleton.gif` - Profile screen loading animation  
- `dashboard_skeleton.gif` - Dashboard screen loading animation

### Example ffmpeg command:
```bash
ffmpeg -i screen_recording.mp4 -vf "fps=15,scale=320:-1" -loop 0 feed_skeleton.gif
```
