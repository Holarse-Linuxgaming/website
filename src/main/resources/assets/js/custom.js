 function updateUrlWithSize(newSize) {
    const url = new URL(location.href);
    url.searchParams.set("size", newSize);
    location.href = url.href;
};