import argparse
import base64
import string
import sys




ALPHABET_LO = string.ascii_lowercase
ALPHABET_UP = string.ascii_uppercase


def _key_shifts(key: str):
        return [ord(c.lower()) - ord('a') for c in key if c.isalpha()]


def _vigenere_apply(text: str, key_shifts, encrypt=True):
        if not key_shifts:
                key_shifts = [13]
        out = []
        klen = len(key_shifts)
        ki = 0
        for ch in text:
                if ch.islower():
                        base = ord('a')
                        shift = key_shifts[ki % klen]
                        if not encrypt:
                                shift = -shift
                        out.append(chr((ord(ch) - base + shift) % 26 + base))
                        ki += 1
                elif ch.isupper():
                        base = ord('A')
                        shift = key_shifts[ki % klen]
                        if not encrypt:
                                shift = -shift
                        out.append(chr((ord(ch) - base + shift) % 26 + base))
                        ki += 1
                else:
                        # please don't get out of line i will freak out
                        out.append(ch)
        return ''.join(out)


def encrypt_name(plain: str, key: str = None, mode: str = 'vigenere') -> str:
        mode = mode.lower()
        if mode == 'rot13' or (mode == 'vigenere' and not key):
                transformed = _vigenere_apply(plain, [13], encrypt=True)
        elif mode == 'caesar':
                # simple Caesar by 3 (classic)
                transformed = _vigenere_apply(plain, [3], encrypt=True)
        else:  # vigenere with provided key
                shifts = _key_shifts(key or "")
                transformed = _vigenere_apply(plain, shifts, encrypt=True)

        # oh boy i love base 64 mhm boy oh i do
        b64 = base64.urlsafe_b64encode(transformed.encode('utf-8')).decode('ascii')
        return b64


def decrypt_name(ciphertext: str, key: str = None, mode: str = 'vigenere') -> str:
        mode = mode.lower()
        try:
                decoded = base64.urlsafe_b64decode(ciphertext.encode('ascii')).decode('utf-8')
        except Exception:
                raise ValueError("Invalid ciphertext: not valid base64")

        if mode == 'rot13' or (mode == 'vigenere' and not key):
                return _vigenere_apply(decoded, [13], encrypt=False)
        elif mode == 'caesar':
                return _vigenere_apply(decoded, [3], encrypt=False)
        else:
                shifts = _key_shifts(key or "")
                return _vigenere_apply(decoded, shifts, encrypt=False)


def main(argv=None):
        p = argparse.ArgumentParser(description="Simple name encrypter/decrypter")
        p.add_argument("--name", "-n", help="Plain name to encrypt")
        p.add_argument("--cipher", "-c", help="Ciphertext to decrypt")
        p.add_argument("--key", "-k", help="Key for Vigenere mode (letters only)")
        p.add_argument("--mode", "-m", choices=['vigenere', 'caesar', 'rot13'], default='vigenere')
        p.add_argument("--decrypt", "-d", action='store_true', help="Decrypt instead of encrypt")
        args = p.parse_args(argv)

        if args.decrypt:
                if not args.cipher:
                        print("Provide --cipher to decrypt", file=sys.stderr)
                        sys.exit(2)
                try:
                        plain = decrypt_name(args.cipher, key=args.key, mode=args.mode)
                        print(plain)
                except ValueError as e:
                        print("Error:", e, file=sys.stderr)
                        sys.exit(1)
        else:
                if not args.name:
                        # ENTER A NAME YOU STINKER
                        try:
                                args.name = input("Name to encrypt: ")
                        except EOFError:
                                print("No name provided", file=sys.stderr)
                                sys.exit(2)
                cipher = encrypt_name(args.name, key=args.key, mode=args.mode)
                print(cipher)


if __name__ == "__main__":
        main()