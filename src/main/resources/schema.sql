CREATE TABLE IF NOT EXISTS file (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    extension TEXT NOT NULL,
    size BIGINT NOT NULL,
    create_at TIMESTAMPTZ DEFAULT now(),
    update_at TIMESTAMPTZ DEFAULT now()
);

ALTER TABLE file
     ADD COLUMN IF NOT EXISTS is_used BOOLEAN;
