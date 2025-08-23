# compare pdf api

## utilisation :

### comparaison :


```curl
curl -X POST http://localhost:9000/diff -F "pdf1=@C:\pdfs\test_1.pdf;type=application/pdf" -F "pdf2=@C:\pdfs\test_2.pdf;type=application/pdf"
```