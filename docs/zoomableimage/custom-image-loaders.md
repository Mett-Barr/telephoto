# Custom image loaders

In its essence, `ZoomableImage` is simply an abstraction over an image loading library. If your preferred library isn't supported by `telephoto` out of the box, you can create your own by extending `ZoomableImageSource`.

```kotlin
@Composable
fun ZoomablePicassoImage(
  model: Any?,
  contentDescription: String?,
) {
  ZoomableImage(
    image = ZoomableImageSource.picasso(model),
    contentDescription = contentDescription,
  )
}

@Composable
private fun ZoomableImageSource.Companion.picasso(
  model: Any?,
  picasso: Picasso = Picasso.Builder(LocalContext.current).build(),
): ZoomableImageSource {
  return remember(model, picasso) {
    TODO("See ZoomableImageSource.coil() or glide() for an example.")
  }
}
```

`ZoomableImageSource.picasso()` will be responsible for loading an image and deciding whether it can be shown as-is or should be shown in a sub-sampling image viewer to avoid OOMs. Here are two examples:

- [CoilImageSource](https://github.com/saket/telephoto/blob/trunk/zoomable-image/coil/src/main/kotlin/me/saket/telephoto/zoomable/coil/CoilImageSource.kt)
- [GlideImageSource](https://github.com/saket/telephoto/blob/25072f96ed47871cf827dd9d255edb552b24044e/zoomable-image/glide/src/main/kotlin/me/saket/telephoto/zoomable/glide/GlideImageSource.kt)