#include <iostream>
#include <cmath>
#include <set>
#include <vector>
inline int addDigits(int x) {
    int d = 0;
    int n = x;
    while (n > 0) {
        d += (n % 10);
        n /= 10;
    }
    return d;
}

typedef struct ps { 
    int x;
    int y;
    friend bool operator==(const struct ps &a, const struct ps &b);
    friend bool operator<(const struct ps &a, const struct ps &b);
} pt;

bool operator==(const pt &a, const pt &b) {
    return (a.x == b.x && a.y == b.y);
}
bool operator<(const pt &a, const pt &b) {
    if (a.x == b.x) {
        return (a.y < b.y);
    } else {
        return (a.x < b.x);
    }
}
inline bool feasible(const pt &p)
{
    int ax = std::abs(p.x);
    int ay = std::abs(p.y);

    int sd = addDigits(ax) + addDigits(ay);
    if (sd <= 19) {
        return true;
    } else {
        return false;
    }
}

inline void checkAndAdd(std::set<pt> &s, const pt &u);
void move(std::set<pt> &s, const pt &cur);

inline void checkAndAdd(std::set<pt> &s, const pt &u)
{
    if (feasible(u) && s.count(u) == 0) {
        s.insert(u);
        move(s, u);
    }
    return;
}

void move(std::set<pt> &s, const pt &cur)
{
    pt u = { cur.x, cur.y + 1 };
    pt d = { cur.x, cur.y - 1 };
    pt l = { cur.x - 1, cur.y };
    pt r = { cur.x + 1, cur.y };

    checkAndAdd(s, u);
    checkAndAdd(s, d);
    checkAndAdd(s, l);
    checkAndAdd(s, r);
    return;
}

int movec(std::vector<bool> &fg, const pt &cur, const int W, const int H)
{
    if (cur.x < 0 || cur.y < 0) {
        return 0;
    }
    const int idx = (cur.y) * W + (cur.x);
    if (fg[idx]) {
        pt u = { cur.x, cur.y + 1 };
        pt d = { cur.x, cur.y - 1 };
        pt l = { cur.x - 1, cur.y };
        pt r = { cur.x + 1, cur.y };
        int c = 1;
        fg[idx] = false;
        c += movec(fg, u, W, H);
        c += movec(fg, d, W, H);
        c += movec(fg, l, W, H);
        c += movec(fg, r, W, H);
        return c;
    }
    return 0;
}

int main()
{
    const int W = 300;
    const int H = 300;
    std::vector<bool> fg(W*H, false);

    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            // pt p = {j - W/2, i - H/2};
            pt p = {j, i};
            if (feasible(p)) {
                fg[i*W + j] = true;
            }
        }
    }

    pt start = { 0, 0 };
    const int one_quadrant_both_axes = movec(fg, start, W, H);
    const int one_axis = 299;
    // one for starting point
    std::cout << (4 * (one_quadrant_both_axes - one_axis) + 1) << '\n';
    /*
    std::set<pt> allmoves;
    allmoves.insert(start);
    move(allmoves, start);
    std::cout << allmoves.size() << '\n';
    */
    /*
    std::set<pt>::iterator it = allmoves.begin();
    while (it != allmoves.end()) {
        const pt &p = *it;
        std::cout << p.x << ", " << p.y << '\n';
        it++;
    }*/
    return 0;
}
